/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileUpdateRoutines;

import database.Common_Var;
import database.DatabaseHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UpdateBook {

DatabaseHandler handler = DatabaseHandler.getInstance() ;
 static final Connection connew = Common_Var.connew;    

  public static void updateMasterBook(String bookcode) {
    
    int m_borrowed = 0;
    int mtotalbook=0;
    
    String query = "Select num_book , borrowed_book , remaining_book "
            + "from books where bookcode = '"+bookcode+"'";    
    ResultSet rs = DatabaseHandler.execQuery( query);
  try {
    while(rs.next()){
      mtotalbook = rs.getInt("num_book");
      m_borrowed = rs.getInt("borrowed_book");
    }
    
    m_borrowed-=1;
    int m_availability  = 0;
    int remaining_book = mtotalbook -m_borrowed;
    if (remaining_book > 0){
      m_availability = 1;
      
    }
     String Updatebook = "Update books set "
              + " borrowed_book = ?,  "
              + " remaining_book = ?,  "
              + " availability = ?  "
              + "where bookcode =  ? ";
      PreparedStatement preparedStmt = connew.prepareStatement(Updatebook);
      preparedStmt.setInt(1, m_borrowed);
      preparedStmt.setInt(2, remaining_book);
      preparedStmt.setInt(3, m_availability);
      preparedStmt.setString(4, bookcode);
      System.out.println("update book statement : "+ Updatebook);
      preparedStmt.executeUpdate();
  } catch (SQLException ex) {
    Logger.getLogger(UpdateBook.class.getName()).log(Level.SEVERE, null, ex);
  }
  }
}