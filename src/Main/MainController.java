/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import addmember.AddMemberController;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import fds.fdBookIssue;
import fds.fdCategory;
import fds.fdMember;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listmember.MemberListController;
import FileUpdateRoutines.UpdateBook;
import generalRoutines.showmessages;
import javafx.scene.layout.StackPane;
import util.LibraryAssistantUtil;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class MainController implements Initializable {
     
  @FXML  private TableView<fdBookIssue> borrower_tableView;
  @FXML  private TableColumn<fdBookIssue, Integer> col_id;
  
  @FXML  private TableColumn<fdBookIssue, String> col_borrower_id;
  @FXML  private TableColumn<fdBookIssue, String> col_borrower_name;
  @FXML  private TableColumn<fdBookIssue, String> col_borrower_issued;
  @FXML  private TableColumn<fdBookIssue, Button> col_borrower_delete;
    
  @FXML  private TextField tf_Borrowed_bookcode;
  @FXML  private TextField tf_Info_bookId;
  @FXML  private TextField tf_Info_memberid;
  @FXML  private Text tx_bookname;
  @FXML  private Text tx_author;
  @FXML  private Text tx_memberName;
  @FXML  private Text tx_memberInfo;
  @FXML  private Button btn_addMember;
  @FXML  private Text tx_availability;
  @FXML  private Button btn_issue;
  

  ObservableList<fdBookIssue> list_BookIssue = FXCollections.observableArrayList();
  Connection connew = null;
  DatabaseHandler  handler;
  int flag_book = 0;
  int flag_member = 0;
  @FXML
  private Text tx_mainconnection;
  @FXML
  private StackPane rootPane;
    
  

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    handler =  DatabaseHandler.getInstance();
    Common_Var.showmessage = true;
    tx_mainconnection.setText(handler.connew.toString());
    btn_issue.setDisable(true);
    init_Column_IssueTable();
    
  }
  
  @FXML
  private void getmember(ActionEvent event) {
     clear_member_cache();
    String memberId = tf_Info_memberid.getText().toLowerCase();
    String query = "Select * from member where member_id = '" + memberId + "'";
    showmessages.displayMessage(" Query :" + query);
//    System.out.println(" Query :" + query);
    ResultSet rs = handler.execQuery(query);
      int counter =0;
      try {
        while (rs.next()) {
          counter +=1;

          String name = rs.getString("member_name");
          String mobile = rs.getString("member_mobile");
          String email = rs.getString("member_email");
          fill_memberinfo(name, mobile, email);

  //        System.out.println(" title "+ title);
        }
      } catch (SQLException ex) {
        Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
      }
    if (counter == 0){
      tx_memberName.setText("Member not found");
      tx_memberInfo.setText("");
      
    }else{
      flag_member= 1;
    }
    check_issue_Button();
    
    }

  
  private void fill_memberinfo(String name, String mobile, String email) {
    tx_memberName.setText(name);
  
   tx_memberInfo.setText(email + " / "+ mobile);
  }
  
  @FXML
  private void getBookInfo2(ActionEvent event) {
    clear_book_cache();
    String bookcode = tf_Info_bookId.getText().toUpperCase();
    String query = "Select * from books where bookcode = '" + bookcode + "'";
    showmessages.displayMessage(" Query :" + query);
    
    ResultSet rs = handler.execQuery(query);
    
      int count_rec = 0;
      int book_availability = 0;
      try {
        while (rs.next()) {
          count_rec +=1;
          String title = rs.getString("booktitle");
          String author = rs.getString("author_name");
          String publisher = rs.getString("publisher");
//          Boolean availability = rs.getBoolean("availability");
//          String  status = (availability)? "available" :"not available";
          int availability = rs.getInt("availability");
          book_availability = availability;

          fill_bookInfo2(title, author, publisher, availability);
          

  //        System.out.println(" title "+ title);
        }
      } catch (SQLException ex) {
        Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
      }
      if (count_rec == 0) {
      
      tf_Info_bookId.setText("");
      tx_bookname.setText("Invalid Book Code");
      tx_author.setText("");
      tx_availability.setText("");
      }else{
        flag_book = 1;
        
      }
      if (book_availability == 0){
        flag_book =0;
      }
      check_issue_Button();
    
  }
  
  public void fill_bookInfo2(String title, String author, String publisher, int availability) {
    tx_bookname.setText(title);
    tx_author.setText(author);
//    tx_availability.setText(status);
    if (availability == 1 ){
      tx_availability.setText("Available");
    }else {
      tx_availability.setText("Not Available");
    }
    
  }  
  void loadWindow(String loc, String title){
    try {
      Parent parent =FXMLLoader.load(getClass().getResource(loc));
      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setTitle(title);
      stage.setScene(new Scene(parent));
      stage.show();
      LibraryAssistantUtil.setStageIcon(stage);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
          
}


  
  @FXML
  private void issue_book(ActionEvent event) {
    String book_code = tf_Info_bookId.getText();
    String member_id = tf_Info_memberid.getText();
    
    Boolean flag =  book_code.isEmpty() ||member_id.isEmpty();
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      saveBookIssue(book_code,member_id);
    }        
            
  }

  private void check_issue_Button() {
    if (flag_book == 1 && flag_member == 1){
      btn_issue.setDisable(false);
    }
  }
  void clear_cache(){
  clear_book_cache();
  clear_member_cache();
  
}

  @FXML
  private void clear_book_cache() {
    tx_bookname.setText("");
    tx_author.setText("");
    tx_availability.setText("");
  }

  @FXML
  private void clear_member_cache() {
    tx_memberName.setText("");
    tx_memberInfo.setText("");
  }

  private void saveBookIssue(String book_code, String member_id) {
      String query = "Insert into bookissuelog "
       + "( member_id, bookcode)"
       + " values ("
       + "'" + member_id + "',"
       + "'" + book_code 
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
      
    btn_issue.setDisable(true);
    Update_library_issuebook(book_code);
    
  }

  private void Update_library_issuebook(String book_code) {
    Connection connew = Common_Var.connew;
    int totalbook = 0;
    int borrowedBook = 0;        
    String query = "Select num_book , borrowed_book "
            + "from books where bookcode = '"+book_code+"'";    
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        totalbook = rs.getInt("num_book");
        borrowedBook = rs.getInt("borrowed_book");
      }
    } catch (SQLException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
    borrowedBook += 1;
    int remaining_book = totalbook - borrowedBook;
    int m_availability = 0;

    if (remaining_book > 0){
      m_availability = 1;
    }
    
    
    try {
      String Updatebook = "Update books set "
              + " borrowed_book = ?,  "
              + " remaining_book = ?,  "
              + " availability = ?  "
              + "where bookcode =  ? ";
      
      
      PreparedStatement preparedStmt = connew.prepareStatement(Updatebook);
      preparedStmt.setInt(1, borrowedBook);
      preparedStmt.setInt(2, remaining_book);
      preparedStmt.setInt(3, m_availability);
      
      preparedStmt.setString(4, book_code);
      
      showmessages.displayMessage("update book statement : "+ Updatebook);
      
      
      preparedStmt.executeUpdate();
      connew.setAutoCommit(true);
    } catch (SQLException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  @FXML
  private void get_ListBorrowedBooks(ActionEvent event) {
    String book_code =tf_Borrowed_bookcode.getText();
    Boolean flag =  book_code.isEmpty() ;
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="Enter a book...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      
      
      queryLeftJoinBooksMember(book_code);
    }
   
  }
  private void init_Column_IssueTable() {
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_borrower_id.setCellValueFactory(new PropertyValueFactory<>("memberid"));
    col_borrower_name.setCellValueFactory(new PropertyValueFactory<>("membername"));
    col_borrower_issued.setCellValueFactory(new PropertyValueFactory<>("issued_date"));
    col_borrower_delete.setCellValueFactory(new PropertyValueFactory<>("btn_del"));
    
  }
  private void queryLeftJoinBooksMember(String book_code) {
    list_BookIssue.clear();
    try {
      String query
       = "SELECT bookissuelog.id, bookissuelog.member_id , member.member_name , "
              + "bookissuelog.bookcode, bookissuelog.issuedate  " 
              +" FROM bookissuelog left join  member "
              + "on bookissuelog.member_id = member.member_id "
              + "where bookissuelog.bookcode = '"+book_code+"'";
      ResultSet rs = handler.execQuery( query);
      showmessages.displayMessage("query on bookissuelog/member : \n"+ query  );
      
      
      while (rs.next()) {
        int id = rs.getInt("id");
        String membercode = rs.getString("member_id");
        String membername = rs.getString("member_name");
        String bookcode = rs.getString("bookcode");
        String dateissue = rs.getString("issuedate");        
        Button btndelete = new Button();
          btndelete.setText("Delete");
          btndelete.setOnAction(e -> {  // delete button
            showmessages.displayMessage("button delete" + id + " bookissuelog");
            
            deleteFromBookIssue(id,bookcode);

                });
        list_BookIssue.add(new fdBookIssue(id, membercode, membername,bookcode, 
                dateissue, btndelete));
      } } catch (SQLException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
    borrower_tableView.getItems().setAll(list_BookIssue);

}

  private  void deleteFromBookIssue(int id,String bookcode) {
    try {
      connew.setAutoCommit(false);
      PreparedStatement myStmt_deleterec = null;
      String mysql_dele = "delete from "
              + "bookissuelog "
              + "where id = " + id;
      myStmt_deleterec = connew.prepareStatement(mysql_dele);
      myStmt_deleterec.executeUpdate();
      connew.commit();
      connew.setAutoCommit(true);
      
      
    } catch (SQLException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
//    joven
//    UpdateBook.updateMasterBook(bookcode);
    refresh_list();
  }

  private void refresh_list() {
    queryLeftJoinBooksMember(tf_Borrowed_bookcode.getText());
  }

  
  /*  ------------------------
 * call
  ------------------------ */  
  private void call_addTempInvoice(ActionEvent event) {
    loadWindow("/temp_invEntry/invEntry.fxml","Add Temp Invoice Entry");
  }
  @FXML
  private void call_settings(ActionEvent event) {
      loadWindow("/Settings/Settings.fxml","Settings");
  }

  private void call_displayInvoiceTemp(ActionEvent event) {
    loadWindow("/temp_inv/invtemp.fxml","Temp");
//    loadWindow("/listbook/BookList.fxml","Book List");
  }
  @FXML
  private void handleMenuItem_close(ActionEvent event) {
     ((Stage) rootPane.getScene().getWindow()).close();
  }
  /*  --------  Book Add ---------- */  
  @FXML
  private void call_addBook(ActionEvent event) {
    load_Books_Add();
  }  
  @FXML
  private void handleMenuItem_addbooks(ActionEvent event) {
    load_Books_Add();
  }
  void load_Books_Add(){
  loadWindow("/addbook/FXMLDocument.fxml","Add Book");
  }
  
  /*  --------  Book display /List ---------- */  
  @FXML
  private void call_displayBooks(ActionEvent event) {
    
    load_Books_List();
  }
  @FXML
  private void handleMenuItem_ListBooks(ActionEvent event) {
  
  load_Books_List();
  }
  void load_Books_List(){
     loadWindow("/listbook/BookList.fxml","Book List");
   }
  /*  --------  Brand add ---------- */  
  @FXML
  private void handleMenuItem_AddBrand(ActionEvent event) {
    loadWindow("/BrandAdd/brandAdd.fxml","Brand Add REcord");
  }
  
  @FXML
  
  private void handleMenuItem_BookIssueList(ActionEvent event) {
     loadWindow("/BookViewIssues/BookViewIssues.fxml","View List");
  }
  

  
  /*  --------  Brand List ---------- */  
   @FXML
  private void handleMenuItem_ListBrand(ActionEvent event) {
    loadWindow("/BrandList/brandlist.fxml","Temp");
  }
  

  /*  --------  Category List ---------- */  
  @FXML
  private void handleMenuItem_ListCategory(ActionEvent event) {
    load_Category_list();
  }
  void load_Category_list(){
    loadWindow("/CategoryList/CategoryList.fxml","Category list");
  }  

  /*  --------  Category Add ---------- */  
    @FXML
  private void handleMenuItem_addcategory(ActionEvent event) {
     load_Category_Add();
  }
  void load_Category_Add(){
  loadWindow("/CategoryAdd/CategoryAdd.fxml","Category Add REcord");
  }

  /*  --------  Member Add ---------- */  
    @FXML
  private void call_addMember(ActionEvent event) {
    load_Member_Add();
  }
  @FXML
  private void handleMenuItem_addmember(ActionEvent event) {
    load_Member_Add();
  }
  void load_Member_Add(){
    loadWindow("/addmember/addMember.fxml","Add to Member (table)");
  }

  /*  --------  Member List ---------- */  
  @FXML
  private void call_displayMembers(ActionEvent event) {
    load_Members_List();
  }
  @FXML
  private void handleMenuItem_ListMember(ActionEvent event) {
    load_Members_List();
  }
  void load_Members_List(){
      loadWindow("/listmember/MemberList.fxml","Member List");
  
  };
  /*  --------  temp invoice ---------- */
  @FXML
  private void handleMenuItem_addTempInvoice(ActionEvent event) {
//    loadWindow("/temp_invEntry/invEntry.fxml","Add Temp Invoice Entry");
    loadWindow("/TempInvoiceAdd/tempInvoiceAdd.fxml","Add Temp Invoice Entry 2");
  }
 
  private void call_addInvoice2(ActionEvent event) {
    loadWindow("/TempInvoiceAdd/tempInvoiceAdd.fxml","Add Temp Invoice Entry 2");
  }  
  @FXML
  private void handleMenuItem_ListTempInvoice(ActionEvent event) {
  loadWindow("/temp_inv/invtemp.fxml","Temp");
  }

  /*  --------  xx ---------- */

  @FXML
  private void handleMenuItem_FullScreen(ActionEvent event) {
    Stage stage= ((Stage) rootPane.getScene().getWindow());
    stage.setFullScreen(!stage.isFullScreen());
  }


}