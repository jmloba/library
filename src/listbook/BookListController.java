/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listbook;

import FileUpdateRoutines.UpdateBook;
import addbook.FXMLDocumentController;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import fds.fdBook;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.LibraryAssistantUtil;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class BookListController implements Initializable {

  @FXML
  private AnchorPane rootPane;
  @FXML
  private TableView<Book> table_View;
  @FXML
  private TableColumn<Book, String> col_code;
  @FXML
  private TableColumn<Book, String> col_title;
  @FXML
  private TableColumn<Book, String> col_author;
  @FXML
  private TableColumn<Book, String> col_publisher;
  @FXML
  private TableColumn<Book, Boolean> col_availability;
  @FXML
  private TableColumn<Book, Integer> col_bookOnShelf;

  static Connection connew = null;
  ObservableList<Book> list = FXCollections.observableArrayList();

  DatabaseHandler handler;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    handler = DatabaseHandler.getInstance();
    boolean showmessage = Common_Var.showmessage;
    viewCurrentConnection.showCurrentConnection("Booklist Controller ", "booklist", connew);

    init_column();

    loadData();

  }

  private void init_column() {
    col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
    col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
    col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
    col_publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    col_availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    col_bookOnShelf.setCellValueFactory(new PropertyValueFactory<>("book_onShelf"));
  }

  private void loadData() {
//    DatabaseHandler  handler = new DatabaseHandler();
    list.clear();
    String query = "Select * from books";
    ResultSet rs = handler.execQuery(query);

    try {
      while (rs.next()) {
        String code = rs.getString("bookcode");
        String title = rs.getString("booktitle");
        String author = rs.getString("author_name");
        String publisher = rs.getString("publisher");
        Boolean availability = rs.getBoolean("availability");
        Integer book_onShelf = rs.getInt("remaining_book");

        list.add(new Book(code, title, author, publisher, availability, book_onShelf));
        showmessages.displayMessage("code :" + code
                + ",  title:" + title
                + ",  author:" + author
                + ",  publisher:" + publisher
                + ",  book_onShelf:" + book_onShelf
                + ",  availability:" + availability);

      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.setItems(list);

  }

  @FXML
  private void handleBookDeleteOption(ActionEvent event) {

    // fetch the selected object
    Book selectedForDeletion = table_View.getSelectionModel().getSelectedItem();
    if (selectedForDeletion == null) {
      System.out.println("no item for deletion");
      AlertMaker.AlertMaker.showErrorMessage("No record selected", "please select record");
    } else {
      System.out.println("selected : " + selectedForDeletion);

      if (!DatabaseHandler.getInstance().isBookAreadyIssued(selectedForDeletion)) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Book");
        alert.setContentText("Are you sure you want to delete this record");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
//          delete_From_Book(selectedForDeletion.code) ;
          // using prepared statement
          Boolean result = DatabaseHandler.getInstance().deleteBook(selectedForDeletion);
          if (result) {

            AlertMaker.AlertMaker.showErrorMessage(" Deleted", "Deletion OK");
            list.remove(selectedForDeletion);
          }
        } else {
          AlertMaker.AlertMaker.showErrorMessage(" Cancelled", "Deletion cancelled");
        }

      } else {
        AlertMaker.AlertMaker.showErrorMessage("Book is already borrowed", "Cant delete this book");
      }
//      loadData();
    }
  }

  private boolean allowedForDeletion(SimpleStringProperty code) {
    Integer borrowedBook = 0;
    try {
      String query = "Select * from books where bookcode = '" + code.get() + "'";
      ResultSet rs = handler.execQuery(query);
      while (rs.next()) {
        borrowedBook = rs.getInt("borrowed_book");
      }
      if (borrowedBook == 0) {
        return true;
      }

    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  private void delete_From_Book(SimpleStringProperty code) {
    String query = "delete from "
            + "books "
            + "where bookcode = '" + code.get() + "'";
    if (handler.execAction(query)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      String alert_message = "Success";
      alert.setContentText(alert_message);
      alert.showAndWait();
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message = "Failed";
      alert.setContentText(alert_message);
      alert.showAndWait();
    }
  }

  @FXML
  private void handleEditOption(ActionEvent event) {
    System.out.println("edit a record");
    Book selectedForEdit = table_View.getSelectionModel().getSelectedItem();
    if (selectedForEdit == null) {
      AlertMaker.AlertMaker.showErrorMessage("No book is selected", "please select a book");
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/addbook/FXMLDocument.fxml"));
      Parent parent = loader.load();

      // note : FXMLDocumentController  -> from addbook
      FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
      controller.inflateUI(selectedForEdit);

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setTitle("Edit Book");
      stage.setScene(new Scene(parent));
      stage.show();
      LibraryAssistantUtil.setStageIcon(stage);
      
      stage.setOnCloseRequest((e)->{
        handleRefreshList(new ActionEvent());
      
      });
      
    } catch (IOException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @FXML
  private void handleRefreshList(ActionEvent event) {
    loadData();
  }

  public static class Book {

    public final SimpleStringProperty code;
    public final SimpleStringProperty title;
    public final SimpleStringProperty author;
    public final SimpleStringProperty publisher;
    public final SimpleBooleanProperty availability;
    public final SimpleIntegerProperty book_onShelf;
    
    
   


    public Book(String code, String title, String author, String publisher,
            Boolean availability, int book_onShelf) {
      this.code = new SimpleStringProperty(code);
      this.title = new SimpleStringProperty(title);
      this.author = new SimpleStringProperty(author);
      this.publisher = new SimpleStringProperty(publisher);
      this.availability = new SimpleBooleanProperty(availability);
      this.book_onShelf = new SimpleIntegerProperty(book_onShelf);
      

    }


    public String getCode() {
      return code.get();
    }

    public String getTitle() {
      return title.get();
    }

    public String getAuthor() {
      return author.get();
    }

    public String getPublisher() {
      return publisher.get();
    }

    public Boolean getAvailability() {
      return availability.get();
    }

    public Integer getBook_onShelf() {
      return book_onShelf.get();
    }

//    public void setCode(SimpleStringProperty code) {
//      this.code = code;
//    }
//    public void setTitle(SimpleStringProperty title) {
//      this.title = title;
//    }
//    public void setAuthor(SimpleStringProperty author) {
//      this.author = author;
//    }
//    public void setPublisher(SimpleStringProperty publisher) {
//      this.publisher = publisher;
//    }
//    public void setAvailability(SimpleBooleanProperty availability) {
//      this.availability = availability;
//    }
//    
  }

}
