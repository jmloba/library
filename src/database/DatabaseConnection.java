/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JovenLoba
 */
public class DatabaseConnection {
  public Connection databaseLink;
   private Connection connect_to_x = null;
  private final String username = "root";
  private final String password = "Acoje1985**";
  private final String localhost = "jdbc:mysql://localhost:3306/library";
  private Object ex;

  public Connection getConnection(){
    System.out.println("passed in get connection");
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect_to_x = DriverManager.getConnection(localhost, username, password);
      } catch(ClassNotFoundException ex){
        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        
        ex.printStackTrace();
      }
    return connect_to_x;
      
    
  }
  
}
