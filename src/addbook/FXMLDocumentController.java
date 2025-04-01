package addbook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import database.Common_Var;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import generalRoutines.viewCurrentConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.activation.DataHandler;

public class FXMLDocumentController implements Initializable {

  @FXML
  private TextField tf_BookTitle;
  @FXML
  private TextField tf_BookCode;
  @FXML
  private TextField tf_BookAuthor;
  @FXML
  private TextField tf_BookPublisher;
  @FXML
  private Button btn_save;
  @FXML
  private Button btn_cancel;
  
  Connection connew = null;
  DatabaseHandler handler;
  
  @FXML
  private AnchorPane rootPane;
  @FXML
  private TextField tf_quantity;

  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
//    DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    boolean showmessage = Common_Var.showmessage;
    connew = Common_Var.connew ;
    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);
    
  }
  @FXML
  private void addBook(ActionEvent event) {
//    Connection connew = Common_Var.connew ;
    String bookCode = tf_BookCode.getText().toUpperCase(Locale.ITALY);
    String bookTitle = tf_BookTitle.getText();
    String bookAuthor = tf_BookAuthor.getText();
    String bookPublisher = tf_BookPublisher.getText();
    Integer bookquantity = Integer.valueOf(tf_quantity.getText());
    
    if (bookCode.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message = "All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else {
      saveBook(bookCode, bookTitle, bookAuthor, bookPublisher, bookquantity );
    }
  }

  @FXML
  private void cancelBook(ActionEvent event) {
    Stage stage = (Stage) rootPane.getScene().getWindow();
    stage.close();
  }

  private void saveBook(String code, String title, String author, String publisher , int quantity) {
    int avail_flag = 0;
    if (quantity >0){
      avail_flag = 1;
    }    
    String query = "Insert into books (bookcode, booktitle, author_name, publisher, num_book , remaining_book, availability) "
            //      String query="Insert into books  "
            + " values ("
            + "'" + code + "',"
            + "'" + title + "',"
            + "'" + author + "',"
            + "'" + publisher  + "',"
            + "'" + quantity  + "',"
            + "'" + quantity  + "',"
            
            + "'" + avail_flag 
            +"')";

    System.out.println("query : " + query);
      if(handler.execAction(query)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        String alert_message ="Success";
        alert.setContentText(alert_message);
        alert.showAndWait();
      }else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        String alert_message ="Failed";
        alert.setContentText(alert_message);
        alert.showAndWait();
      }     
  }

  private void checkData() {
    String query = "Select booktitle from books";
    ResultSet rs = handler.execQuery(query);
    try {
      while (rs.next()) {
        String title = rs.getString("booktitle");
        System.out.println("title :" + title);
      }
    } catch (SQLException ex) {
      Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
}
