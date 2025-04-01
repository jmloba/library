/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listbook;

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class BookListController implements Initializable {
  ObservableList<Book> list = FXCollections.observableArrayList();
  
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
  
  static DatabaseHandler handler;
  static Connection connew = null;
  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
//     DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    handler = DatabaseHandler.getInstance() ;
    boolean showmessage = Common_Var.showmessage;
    viewCurrentConnection.showCurrentConnection("BrandList ", "Brand List", connew);
    
    
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
    String query = "Select * from books";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        String code = rs.getString("bookcode");
        String title = rs.getString("booktitle");
        String author = rs.getString("author_name");
        String publisher = rs.getString("publisher");
        Boolean availability = rs.getBoolean("availability");
        Integer book_onShelf = rs.getInt("remaining_book");
        
        
        list.add(new Book(code,title,author,publisher,availability,book_onShelf));
        showmessages.displayMessage("code :"+ code +
                ",  title:" + title+
                ",  author:" + author+
                ",  publisher:" + publisher+
                ",  book_onShelf:" + book_onShelf+
                ",  availability:" + availability);
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.getItems().setAll(list);
    
    
  }

  public static class  Book{
    private final SimpleStringProperty code;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty publisher;
    private final SimpleBooleanProperty availability;
    private final SimpleIntegerProperty book_onShelf;
    
    Book(String code, String title, String author, String publisher, 
            Boolean availability, int book_onShelf ){
      this.code = new SimpleStringProperty(code);
      this.title = new SimpleStringProperty(title);
      this.author = new SimpleStringProperty(author);
      this.publisher = new SimpleStringProperty(publisher);
      this.availability = new SimpleBooleanProperty(availability);
      this.book_onShelf = new SimpleIntegerProperty(book_onShelf);
      
    }

    public String getCode() { return code.get(); }
    public String getTitle() { return title.get(); }
    public String getAuthor() { return author.get(); }
    public String getPublisher() { return publisher.get();    }
    public Boolean getAvailability() {return availability.get();    }
    public Integer getBook_onShelf() { return book_onShelf.get();   }
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
