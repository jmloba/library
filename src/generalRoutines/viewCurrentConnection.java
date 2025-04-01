/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRoutines;

import database.Common_Var;
import database.DatabaseHandler;
import java.sql.Connection;

/**
 *
 * @author JovenLoba
 */
public class viewCurrentConnection {
  DatabaseHandler handler = DatabaseHandler.getInstance() ;
  static final Connection connew = Common_Var.connew;    
  static boolean showmessage = Common_Var.showmessage;    
  public static void showCurrentConnection(String packageRoutine,String desc ,Connection myconnection){
    if (showmessage) {
    System.out.println("Routine : "+ packageRoutine);
    System.out.println("Description : "+ desc);
    System.out.println("Connection : "+myconnection.toString());
    }
  
  }
 
  
  
 
 
}

