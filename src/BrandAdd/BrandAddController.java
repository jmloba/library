
package BrandAdd;

import addmember.AddMemberController;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import generalRoutines.routineInitialization;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BrandAddController implements Initializable {

  @FXML  private TextField tf_BrandCode;
  @FXML  private TextField tf_BrandDescription;
  @FXML  private Button btn_save;
  @FXML  private Button btn_cancel;
  
  @FXML
  private AnchorPane rootPane;

  Connection connew = null;
  DatabaseHandler  handler;
  

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
//     DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("BrandAdd", "Add REcord Brand entry ", connew);
    checkdata();
  }  


   private void checkdata() {
    String query = "Select brandcode, brandname from brand";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        String name = rs.getString("brandcode");
        String desc = rs.getString("brandname");
        
        showmessages.displayMessage("brandcode:"+ name + ", Desc :"+desc);
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @FXML
  private void cancelBrand(ActionEvent event) {
    Stage stage = (Stage) rootPane.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void addBrand(ActionEvent event) {
    String code = tf_BrandCode.getText();
    String desc = tf_BrandDescription.getText();
    Boolean flag =  code.isEmpty() ||desc.isEmpty();
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      saveBrand(code,desc);
    }
  }

  private void saveBrand(String code, String desc) {
    String query="Insert into brand (brandcode, brandname) "
      + " values (" 
      + "'"+  code     + "',"
      + "'"+  desc    +  "')";
    showmessages.displayMessage("query : "+ query);

  if(handler.execAction(query)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        String alert_message ="Success";
        alert.setContentText(alert_message);
        alert.showAndWait();
      }else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        String alert_message ="Failed";
        alert.setContentText(alert_message);
        alert.showAndWait();
      }              
  }
}
