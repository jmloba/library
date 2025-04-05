

package addmember;


import database.DatabaseHandler;
import database.Common_Var;
import database.DatabaseConnection;
import fds.fdMember;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;
import generalRoutines.routineInitialization;


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

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class AddMemberController implements Initializable {

  @FXML
  private TextField tf_name;
  @FXML
  private TextField tf_id;
  @FXML
  private TextField tf_email;
  @FXML
  private TextField tf_mobile;
  @FXML
  private Button btn_save;
  @FXML
  private Button btn_cancel;
  @FXML
  private AnchorPane rootPane;
  
  DatabaseHandler handler;
  private Boolean isInEditMode= Boolean.FALSE;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    handler =  DatabaseHandler.getInstance();
    boolean showmessage = Common_Var.showmessage;
    
    

    checkdata();
  }  

  @FXML
  private void addMember(ActionEvent event) {
    String id = tf_id.getText();
    String name = tf_name.getText();
    String email = tf_email.getText();
    String mobile = tf_mobile.getText();
    String bookAvailability = "1";
    
    
    Boolean flag = id.isEmpty()|| name.isEmpty() ||email.isEmpty() || mobile.isEmpty();
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } 
    if(isInEditMode){
      handleEditOperation();
    }
    else{
    
      saveMember(id,name,email,mobile);
    }
      
      
  }

  @FXML
  private void cancelMember(ActionEvent event) {
    Stage stage = (Stage) rootPane.getScene().getWindow();
    stage.close();
  }

  private void saveMember(String id, String name, String email, String mobile) {
    String query="Insert into member (member_id, member_name, member_mobile,member_email) "
      + " values (" 
      + "'"+id      + "',"
      + "'"+name    + "',"
      + "'"+mobile  + "',"
      + "'"+email   + "')";
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
    String query = "Select member_name from member";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        
        String name = rs.getString("member_name");
        showmessages.displayMessage("name :"+ name);
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void inflateUI(fdMember selectedForEdit) {
    tf_name.setText(selectedForEdit.member_name);
    tf_id.setText(selectedForEdit.member_id);
    tf_email.setText(selectedForEdit.member_email);
    tf_mobile.setText(selectedForEdit.member_mobile);
    
    tf_id.setDisable(true);
    isInEditMode= Boolean.TRUE;
    
    
  }

  private void handleEditOperation() {
    fdMember member = new fdMember(
      tf_id.getText(),      tf_name.getText(),
      tf_mobile.getText(),  tf_email.getText()
    );
    if(handler.updateMember(member)){
       AlertMaker.AlertMaker.showSimpleAlert("Member Updated", "Member record has been saved");
    }else{
      AlertMaker.AlertMaker.showErrorMessage("Member Update Failed", "cannot update member table");
    }
            
    
    
  }
   
   
}
