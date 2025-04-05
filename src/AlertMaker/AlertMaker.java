/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlertMaker;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author JovenLoba
 */
public class AlertMaker {
  public static void showSimpleAlert(String title, String content){
  Alert alert = new Alert(AlertType.INFORMATION);
  alert.setTitle(title);
  alert.setHeaderText(null);
  alert.setContentText(content);
  alert.showAndWait();
  
  }
  public static void showErrorMessage(Exception ex){
  Alert alert = new Alert(AlertType.ERROR);
  alert.setTitle("Error Occured");
  alert.setHeaderText(null);
  alert.setContentText(ex.toString());
  alert.showAndWait();
  
  }
  
  
   public static void showErrorMessage(Exception ex,String title, String content){
  Alert alert = new Alert(AlertType.ERROR);
  alert.setTitle(title);
  alert.setHeaderText(null);
  alert.setContentText(content);
  alert.setContentText(ex.toString());
  alert.showAndWait();
  
  } 
  public static void showErrorMessage(String title, String content){
  Alert alert = new Alert(AlertType.ERROR);
  alert.setTitle(title);
  alert.setHeaderText(null);
  alert.setContentText(content);
//  alert.setContentText(ex.toString());
  alert.showAndWait();
  
  }
}
