/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrandList;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import temp_inv.invtemp;

/**
 *
 * @author JovenLoba
 */
public class brandlist extends Application {
  
  @Override
  public void start(Stage stage) {
  
    try {
      Parent root = FXMLLoader.load(getClass().getResource("brandlist.fxml"));
      Scene scene = new Scene(root, 700, 400);
      stage.setScene(scene);
      stage.show();
    } catch (IOException ex) {
      Logger.getLogger(brandlist.class.getName()).log(Level.SEVERE, null, ex);
    }
  
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}
