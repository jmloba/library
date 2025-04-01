
package CategoryAdd;

import addmember.AddMemberController;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
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

public class CategoryAddController implements Initializable {

  @FXML  private TextField tf_CategoryCode;
  @FXML  private TextField tf_CategoryDescription;
  @FXML  private Button btn_save;
  @FXML  private Button btn_cancel;
  @FXML  private AnchorPane rootPane;
    Connection connew = null;
  static DatabaseHandler  handler;
  

  @Override
  public void initialize(URL url, ResourceBundle rb) {
//    DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    boolean showmessage = Common_Var.showmessage;
    connew = Common_Var.connew ;
    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);
    
    checkdata();
  }  

  @FXML
  private void addCategory(ActionEvent event) {
    String code = tf_CategoryCode.getText();
    String desc = tf_CategoryDescription.getText();
    
    
    
    Boolean flag =  code.isEmpty() ||desc.isEmpty();
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      saveCategory(code,desc);
            }
  }

  @FXML
  private void cancelCategory(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
    stage.close();

  }
 private void saveCategory(String code, String desc) {
String query="Insert into categ (categorycode, categoryname) "
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

private void checkdata() {
    String query = "Select * from categ";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        String name = rs.getString("categorycode");
        String desc = rs.getString("categoryname");
        showmessages.displayMessage("brandcode:"+ name + ", Desc :"+desc);
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
}