
package Settings;

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
import database.DatabaseHandler;

/**
 *
 * @author JovenLoba
 */
public class Settings extends Application {
  DatabaseHandler handler;
  
  @Override
  public void start(Stage stage) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
      Scene scene = new Scene(root, 450, 300);
      stage.setScene(scene);   
      stage.setTitle("Settings");
      stage.show();
      new Thread(()->{
        handler = DatabaseHandler.getInstance() ;
      }).start();
      
      
      
      
    } catch (IOException ex) {
      Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
    }
   
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}
