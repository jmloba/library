/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRoutines;

import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import java.sql.Connection;

/**
 *
 * @author JovenLoba
 */
public class routineInitialization {
  DatabaseConnection connect;
  static Connection connew;
  static DatabaseHandler  handler;
  
  public static void allRoutineInitialization(){
//    DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
//    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);

    
  }
  
}
