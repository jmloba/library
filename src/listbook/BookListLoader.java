/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listbook;

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

/**
 *
 * @author JovenLoba
 */
public class BookListLoader extends Application {
  
  @Override
  public void start(Stage stage) throws IOException {
   
   Parent root = FXMLLoader.load(getClass().getResource("BookList.fxml"));
    Scene scene = new Scene(root, 700, 400);
    stage.setScene(scene);
    stage.show();
  }
    
       

  public static void main(String[] args) {
    launch(args);
  }

  
}
