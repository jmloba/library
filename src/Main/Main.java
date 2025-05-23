/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import database.DatabaseHandler;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.LibraryAssistantUtil;

/**
 *
 * @author JovenLoba
 */
public class Main extends Application {
  DatabaseHandler  handler;
  @Override
  public void start(Stage stage) throws IOException {
//    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    Parent root = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));    
    Scene scene = new Scene(root);
    
    stage.setScene(scene);
    stage.show();
    LibraryAssistantUtil.setStageIcon(stage);
//    DatabaseHandler.getInstance();

    new Thread(()->{
      DatabaseHandler.getInstance();
    
    }).start();
    
    
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}
