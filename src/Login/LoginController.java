/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Main.MainController;
import Settings.Preference;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import util.LibraryAssistantUtil;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class LoginController implements Initializable {

  @FXML
  private TextField tf_username;
  @FXML
  private TextField tf_password;
  @FXML
  private Button btn_login;
  @FXML
  private Button btn_cancel;
  
  @FXML
  private Label login_label;


  Preference preference;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
    preference = Preference.getpreference();
  }


private void closeStage() {
  ((Stage)tf_username.getScene().getWindow()).close();
  }
void loadMain(){
    try {
      Parent parent =FXMLLoader.load(getClass().getResource("/Main/Main.fxml"));
      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setTitle("Library program");
      stage.setScene(new Scene(parent));
      stage.show();
      LibraryAssistantUtil.setStageIcon(stage);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
          
}  
  @FXML
  private void proc_login_cancel(ActionEvent event) {
//    ((Stage)tf_username.getScene().getWindow()).close();
      System.exit(0);
  }

  @FXML
  private void handleButtonAction(ActionEvent event) {
     login_label.setText("Library Assistance Login");
    login_label.setStyle("-fx-background-color :black");
    
    
    String username = tf_username.getText();
    String password = DigestUtils.shaHex(tf_password.getText());
    if ((username.equals(preference.getUsername())) && (username.equals(preference.getUsername()))) {
      // login
    System.out.println(" display");
    System.out.println("username :"+username);
    System.out.println("password :"+password);
    closeStage();
    loadMain();
  }else {
      login_label.setText("Invalid credentials");
      login_label.setStyle("-fx-background-color :#d32f2f; -fx-text-fill:white");
      
    }
  }


  
}

