/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRoutines;

import database.Common_Var;
import database.DatabaseHandler;
import fds.fdInvoiceTemp;
import static generalRoutines.myfunction.handler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 *
 * @author JovenLoba
 */
public class ProgramRoutines {
  static DatabaseHandler  handler;
  static Connection connew = Common_Var.connew ;
    
  public static ObservableList getForComboBrand(){
    ObservableList list_data = FXCollections.observableArrayList();
    String query = "Select id,brandcode, brandname from brand";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id = rs.getInt("id");
        String brandcode = rs.getString("brandcode");
        String brandname = rs.getString("brandname");
        showmessages.displayMessage("id :"+ id +  ",  brandcode:" + brandcode+
          ",  brandname:" + brandname);
        list_data.add(brandname);
      }
    } catch (SQLException ex) {
      Logger.getLogger(myfunction.class.getName()).log(Level.SEVERE, null, ex);
      return null;

    } 
return list_data;      
    
  }
  public static String get_BrandCode (String brandname){
    String code = "";
     String query = "Select brandcode  from brand "
              + "where brandname = '"+brandname+"'";    
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
      
        code = rs.getString("brandcode");
      }
      
      
    } catch (SQLException ex) {
      Logger.getLogger(myfunction.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } 
    return code;
  
  }
  public static ObservableList getForComboCategory(){
    ObservableList list_data = FXCollections.observableArrayList();
    String query = "Select id,categorycode, categoryname from categ";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id = rs.getInt("id");
        String categorycode = rs.getString("categorycode");
        String categoryname = rs.getString("categoryname");
        showmessages.displayMessage("id :"+ id +  ",  categorycode:" + categorycode+
          ",  category name:" + categoryname );
        list_data.add(categoryname);
      }
    } catch (SQLException ex) {
      Logger.getLogger(myfunction.class.getName()).log(Level.SEVERE, null, ex);
      return null;

    } 
return list_data;      
    
  }  
  public static String get_CategoryCode (String categoryname){
    String code = "";
      String query = "Select categorycode  from categ "
              + "where categoryname = '"+categoryname+"'";    
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
    
        code = rs.getString("categorycode");
        return code;      }
    } catch (SQLException ex) {
      Logger.getLogger(myfunction.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } 
    return code;
    
  }  

  
  

}
