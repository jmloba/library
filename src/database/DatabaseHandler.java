package database;


import fds.fdMember;
import generalRoutines.showmessages;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import listbook.BookListController;
import listbook.BookListController.Book;

public  final class DatabaseHandler {

  private static DatabaseHandler  handler = null;
  
  private final String username = "root";
  private final String password = "Acoje1985**";
  private final String localhost = "jdbc:mysql://localhost:3306/library";
  
  public static Statement stmt = null;
  public  static Connection connew = null;
  boolean showmessage = Common_Var.showmessage;
  
  // no object can be created by from any class 
  private   DatabaseHandler(){
    createConnection();
      setup_Book();
      setup_Member();
      setup_Author();
      setup_Publisher();
      setup_Category();
      setup_Brand();
      setup_InvoiceTemp();
      setup_IssueBookTable();

  }
    public static DatabaseHandler getInstance(){
    if (handler == null){
      handler = new DatabaseHandler();
    }
    return handler;
  }
    
    
  void createConnection() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connew = DriverManager.getConnection(localhost, username, password);
      Common_Var.connew = connew;
    } catch(Exception ex){
      JOptionPane.showMessageDialog(null,"cant load database","database error",JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
      System.exit(0);
    }
  }
   
  
  public   ResultSet  execQuery(String query){
    ResultSet result;
    try{
      stmt = connew.createStatement();
      result = stmt.executeQuery(query);
      showmessages.displayMessage("handler connection  : "+ connew);
      
      
    }catch(SQLException ex){
//      jOptionPane.showMessageDialog(null,"Error" + ex.getMessage(),"Error Occured", JOptionPane.ERROR_MESSAGE);
      System.out.println("Exception at execquery : dataHandler"+ex.getLocalizedMessage());
      return null;
    }  finally{
      
    }
    return result;
    
  }

  public  boolean  execAction(String query){
    showmessages.displayMessage("handler: QUERY TO EXECUTE :"+ query);
    try{
      stmt = connew.createStatement();
      stmt.execute(query);
      showmessages.displayMessage("handler connection  : "+ connew);
      return true;
    }    catch(SQLException ex){
//      jOptionPane.showMessageDialog(null,"Error" + ex.getMessage(),"Error Occured", JOptionPane.ERROR_MESSAGE);
      System.out.println("Exception at execquery : dataHandler"+ex.getLocalizedMessage());
    }  
    return false;
  }
  
  
  private void setup_Book() {
    String TABLE_NAME= "books";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key  NOT NULL AUTO_INCREMENT unique, \n"
              + " bookcode  varchar(20) NOT NULL unique,\n"
              + " booktitle varchar(100) NOT NULL ,\n"
              + " category varchar(45),\n"
              + " author_id int   , \n"
              + " author_name varchar(45) NOT NULL ,\n"
              + " publisher varchar(75) NOT NULL ,\n"
              + " num_book int  ,\n"
              + " borrowed_book int  ,\n"
              + " remaining_book int  ,\n"
              + " availability boolean  default false   "
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }
  private void setup_Publisher() {
       String TABLE_NAME= "publisher";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key  NOT NULL  unique auto_increment, \n"
              + " publisher  varchar(45) NOT NULL unique "
              
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }         
  private void setup_Category() {
       String TABLE_NAME= "category";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " cat_code int  primary key  NOT NULL  unique, \n"
              + " cat_desc  varchar(45) NOT NULL ,\n"
              
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }
  private void setup_Author() {
       String TABLE_NAME= "author";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key  NOT NULL AUTO_INCREMENT unique, \n"
              + " firstname  varchar(45) NOT NULL ,\n"
              + " lastname varchar(45) NOT NULL "
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }
  private void setup_Member() {
    String TABLE_NAME= "member";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key unique NOT NULL AUTO_INCREMENT, \n"
              + " member_id  varchar(45) NOT NULL unique,\n"
              + " member_name varchar(80) NOT NULL,\n"
              + " member_mobile varchar(20) NOT NULL unique,\n"
              + " member_email varchar(25) NOT NULL unique "
              +")" );
      }
      
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }

  private void setup_IssueBookTable() {
   String TABLE_NAME= "bookissuelog";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key  NOT NULL AUTO_INCREMENT unique, \n"
              + "  member_id  varchar(45) NOT NULL ,\n"
              + " bookcode  varchar(20) NOT NULL ,\n"
              + " issuedate  timestamp DEFAULT CURRENT_TIMESTAMP ,\n"
              + " renew_count integer default 0 ,\n"
              + " returned_date  DATETIME ,\n"
              + " FOREIGN KEY (BOOKCODE) REFERENCES BOOKS(BOOKCODE), \n"
              + " FOREIGN KEY (member_id) REFERENCES member(member_id) \n"
              
//              + " modified_at DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP "
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }    
  }
  private void setup_InvoiceTemp() {
        String TABLE_NAME= "invoicetemp";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
                
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key unique NOT NULL AUTO_INCREMENT, \n"
              + " itemno  varchar(25) NOT NULL ,\n"
              + " invdate     varchar(10) NOT NULL,\n"
              + " description   varchar(50) NOT NULL ,\n"
              
              + " brandcode varchar(20) ,\n"
              + " categorycode varchar(20) ,\n"
              + " quantity int not null ,\n"
              + " price double not null ,\n"
              + " amount double not null ,\n"
              + " user  varchar(20) ,\n"
              + " createdate DATETIME DEFAULT CURRENT_TIMESTAMP ,\n"
              + " modified DATETIME ON UPDATE CURRENT_TIMESTAMP"
              +")" );
      
      }
      
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }
  private void setup_Brand() {
    String TABLE_NAME= "brand";
    try {
      stmt = connew.createStatement();
      DatabaseMetaData dbm = connew.getMetaData();
      ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() , null);
      if( tables.next()){
        System.out.println(" Table : "+ TABLE_NAME+" ....already exists...");
      }else{
//        create the table here
      stmt.execute("CREATE TABLE " + TABLE_NAME+ "("
              + " id int  primary key unique NOT NULL AUTO_INCREMENT, \n"
              + " brandcode  varchar(20) NOT NULL unique,\n"
              + " brandname  varchar(80) NOT NULL unique"
              +")" );
      }
      
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }
  public Boolean deleteBook(Book book) {
    String deletestmt ="delete from books where bookcode=?";
    PreparedStatement stmt;
    try {
      stmt = connew.prepareStatement(deletestmt);
      stmt.setString(1, book.getCode());      
      int result = stmt.executeUpdate();
      if (result==1){
        return true;
      }else{
        return false;
      }
      
      
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean isBookAreadyIssued(Book book){
    
    try {
      String query = " select count(*) from bookissuelog where bookcode = ?";
      PreparedStatement stmt = connew.prepareStatement(query );
      stmt.setString(1, book.getCode());
      
      ResultSet rs = stmt.executeQuery();
      if(rs.next()){
        int count = rs.getInt(1);
        System.out.println("record count "+ count);
        if (count>0){
          return true;
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
     return false;
  }
  public boolean updateBook(Book book){
    try {
      String update = "update books set booktitle=?, author_name = ? , publisher = ?  "
              + "where bookcode = ?";
      PreparedStatement stmt = connew.prepareStatement(update );
      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getAuthor());
      stmt.setString(3, book.getPublisher());
      stmt.setString(4, book.getCode());
      int res = stmt.executeUpdate();
      if (res >0 ){
        return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
  /*  for member table update
  */
  public boolean updateMember(fdMember member){
  try {
      String update = "update member set member_name=?, member_mobile = ? , member_email = ?  "
              + "where member_id = ?";
      PreparedStatement stmt = connew.prepareStatement(update );
      stmt.setString(1, member.member_name);
      stmt.setString(2, member.member_mobile);
      stmt.setString(3, member.member_email);
      stmt.setString(4,member.member_id);
      int res = stmt.executeUpdate();
      if (res >0 ){
        return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  
  }
  
}

