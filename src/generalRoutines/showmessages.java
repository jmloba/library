/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRoutines;

import database.Common_Var;


public class showmessages {
  static boolean showmessage = Common_Var.showmessage;    
  
  
  
   public static void displayMessage(String message){
     
     
     if (showmessage){
      System.out.println(message);
     }
    
  } 
}
