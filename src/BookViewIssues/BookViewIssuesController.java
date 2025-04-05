/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookViewIssues;

import FileUpdateRoutines.UpdateBook;
import database.Common_Var;
import database.DatabaseHandler;
import fds.fdBookIssue;

import generalRoutines.viewCurrentConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import listbook.BookListController;

import FileUpdateRoutines.UpdateBook;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class BookViewIssuesController implements Initializable {
  
  @FXML
  private TableView<fdBookIssue> table_View;
  @FXML  private TableColumn<fdBookIssue, Integer> col_id;

  @FXML  private TableColumn<fdBookIssue, String> col_memberId;
  @FXML  private TableColumn<fdBookIssue, String> col_memberName;

  @FXML  private TableColumn<fdBookIssue, String> col_bookcode;
  @FXML  private TableColumn<fdBookIssue, String> col_bookName;
  @FXML  private TableColumn<fdBookIssue, String> col_dateIssue;
  @FXML  private TableColumn<fdBookIssue, Button> col_btndelete;
  
  Connection connew = null;
  DatabaseHandler handler;
  ObservableList<fdBookIssue> list = FXCollections.observableArrayList();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    handler = DatabaseHandler.getInstance() ;
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
    
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);
    init_column();
    loadData();
  }  

  private void init_column() {
//    note right variables from fds
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_memberId.setCellValueFactory(new PropertyValueFactory<>("membercode"));
    col_memberName.setCellValueFactory(new PropertyValueFactory<>("membername"));
    
    col_bookcode.setCellValueFactory(new PropertyValueFactory<>("bookcode"));
    col_bookName.setCellValueFactory(new PropertyValueFactory<>("bookname"));
    
    col_dateIssue.setCellValueFactory(new PropertyValueFactory<>("issued_date"));
    col_btndelete.setCellValueFactory(new PropertyValueFactory<>("btn_del"));
  }

  private void loadData() {
 
 String query
       = "SELECT bookissuelog.id, "
              + "bookissuelog.member_id , member.member_name , "
              + "bookissuelog.bookcode, "
                       + "c.booktitle, "
              + "bookissuelog.issuedate  " 
              +" FROM bookissuelog "
         + "left join  member on bookissuelog.member_id = member.member_id "
         + "left join  books as c  on bookissuelog.bookcode = c.bookcode "     ;
    System.out.println("Query: "+ query);
      
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id          = rs.getInt("id");
        String issuedate  = rs.getString("issuedate");
        String membercode  = rs.getString("member_id");
        String membername  = rs.getString("member_name");
        
        String bookcode  = rs.getString("bookcode");
        String booktitle = rs.getString("booktitle");
        Button btndelete = new Button();
          btndelete.setText("Delete");
          btndelete.setOnAction(e -> {  // delete button
          deletefrom_BookIssued(id,bookcode);
          list.clear();
          loadData();
          
        });
        
        list.add(new fdBookIssue(id, issuedate, 
                membercode, membername,  
                bookcode, booktitle, 
                btndelete));
      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.getItems().setAll(list);    
  }


  private void deletefrom_BookIssued(int id, String bookcode) {
    String query = "delete from "
              + "bookissuelog "
              + "where id = " + id;    
     if(handler.execAction(query)){
       UpdateBook.updateMasterBook(bookcode);
        
               
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
  
}
