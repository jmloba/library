/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import static Settings.Preference.getpreference;
import database.Common_Var;
import static database.Common_Var.connew;
import database.DatabaseConnection;
import database.DatabaseHandler;
import generalRoutines.viewCurrentConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class SettingsController implements Initializable {

  @FXML
  private TextField tf_nodaysWOFine;
  @FXML
  private TextField tf_fineDay;
  @FXML
  private TextField tf_username;
  @FXML
  private TextField tf_password;
  @FXML
  private Button btn_save;
  @FXML
  private Button btn_cancel;
  @FXML
  private AnchorPane my_anchorPane;

  /**
   * Initializes the controller class.
   */
  Connection connew = null;
  DatabaseHandler  handler;
  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    
//    DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("BrandAdd", "Add REcord Brand entry ", connew);
    
    initDefaultValues();
  }  

  @FXML
  private void action_btnsave(ActionEvent event) {
     int nDaysWOFine = Integer.valueOf(tf_nodaysWOFine.getText());
    float fine = Float.valueOf(tf_fineDay.getText());
    String username =  tf_username.getText();
    String password = tf_password.getText();
    
    Preference preference = Preference.getpreference();
    preference.setNdaysWOFine(nDaysWOFine);
    preference.setFinePerDay(fine);
    preference.setUsername(username);
    preference.setPassword(password);
    
    Preference.writePreferenceToFile(preference);
   
    
    
    
  }

  @FXML
  private void action_btnCancel(ActionEvent event) {
//    Stage stage = (Stage) my_anchorPane.getScene().getWindow();
//    stage.close();
/*   
 or closing the stage
*/
  ((Stage)tf_nodaysWOFine.getScene().getWindow()).close();


  }

  private void initDefaultValues() {
    Preference preference = Preference.getpreference();
  tf_nodaysWOFine.setText(String.valueOf(preference.getNdaysWOFine()));
  tf_fineDay.setText(String.valueOf(preference.getFinePerDay()));
  tf_username.setText(String.valueOf(preference.getUsername()));
  tf_password.setText(String.valueOf(preference.getPassword()));
  }
  
}
