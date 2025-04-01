/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import generalRoutines.showmessages;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

public  final class DatabaseHandler {
  static final Connection connew = Common_Var.connew;    
  
  private static DatabaseHandler handler = null;
  public static Statement stmt = null;
  boolean showmessage = Common_Var.showmessage;
  private   DatabaseHandler(){
    setuptMember();
    setupBookTable();
    setupAuthor();
    setupcPublisher();
    setupIssueBookTable();
//    for retail
    setupInvoiceTemp();
    setupBrand();
    setupCategory();            
}
  public static DatabaseHandler getInstance(){
    if (handler == null){
      handler = new DatabaseHandler();
    }
    return handler;
  }
  public  static ResultSet  execQuery(String query){

    
    ResultSet result;
    try{
      stmt = connew.createStatement();
      result = stmt.executeQuery(query);
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
      return true;
    }    catch(SQLException ex){
//      jOptionPane.showMessageDialog(null,"Error" + ex.getMessage(),"Error Occured", JOptionPane.ERROR_MESSAGE);
      System.out.println("Exception at execquery : dataHandler"+ex.getLocalizedMessage());
    }  
    return false;
  }
  private void setupcPublisher() {
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
  private void setupcCategory() {
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
  private void setupAuthor() {
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
  private void setupBookTable() {
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
  
  
  private void setuptMember() {
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
/*  for invoices
*/
  private void setupBrand() {
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
  
  private void setupCategory() {
   String TABLE_NAME= "categ";
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
              + " categorycode  varchar(20) NOT NULL unique,\n"
              + " categoryname  varchar(80) NOT NULL unique"
              +")" );
      }
      
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }
  }


  private void setupInvoiceTemp() {
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
  
  
  public void connectionInfo(String mytable){
  System.out.println("opening the table :"+mytable);
  
}

  private void setupIssueBookTable() {
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
              + " issuedate  DATETIME DEFAULT CURRENT_TIMESTAMP ,\n"
              + " returned_date  DATETIME ,\n"
              + " modified_at   DATETIME ON UPDATE CURRENT_TIMESTAMP"
              +")" );
      }
    } catch (SQLException ex) {
       System.err.println(ex.getMessage()+"   .. setup database");
    } finally{
    }    
  }
         
}
