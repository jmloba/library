
package temp_invEntry;

import addmember.AddMemberController;
import database.Common_Var;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class InvEntryController implements Initializable {

  @FXML
  private DatePicker tf_date;
  @FXML
  private TextField tf_Itemno;
  @FXML
  private TextField tf_Desc;
  @FXML
  private TextField tf_BrandCode;
  @FXML
  private TextField tf_CategoryCode;
  @FXML
  private TextField tf_quantity;
  @FXML
  private TextField tf_price;
  @FXML
  private TextField tf_amount;
  @FXML
  private Button btn_save;
  @FXML
  private Button btn_cancel;
    Connection connew = null;
  static DatabaseHandler  handler;
  @FXML
  
  private AnchorPane myPane;
  

  @Override
  public void initialize(URL url, ResourceBundle rb) {
//     DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
//    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_invEntry", "temp invoice entry ", connew);

    checkdata();
  }  

  @FXML
  private void addTempInvoice(ActionEvent event) {
    String date = tf_date.getValue().toString();
    String itemno = tf_Itemno.getText();
    String desc = tf_Desc.getText();
    String brandcode = tf_BrandCode.getText();
    String categorycode = tf_CategoryCode.getText();
    int qty     =Integer.valueOf(tf_quantity.getText());
    double price = Double.valueOf(tf_price.getText());
    double amount = Double.valueOf(tf_amount.getText());
    Boolean flag =  itemno.isEmpty() ||desc.isEmpty() || 
            brandcode.isEmpty() || categorycode.isEmpty();
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      savetemp(date,itemno,desc,
          brandcode,    categorycode,
          qty, price,amount );
    }
  }
private void savetemp(String date, String itemno, String desc,
        String brandcode, String categorycode, 
        int qty, double price, double amount) {
    String query="Insert into invoicetemp (invdate, itemno,description,"
            + "brandcode, categorycode, "
            + "quantity, price, amount)  values ('" +  
            date       + "','" +  itemno     + "','" +    desc       + "','" +
            brandcode  + "','" +  categorycode  + "'," +   
            qty        + "," +
            price     + ","+    amount     + ")";
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

  @FXML
  private void cancelTempInvoice(ActionEvent event) {
        Stage stage = (Stage) myPane.getScene().getWindow();
    stage.close();
  }
    private void checkdata() {
    String query = "Select * from invoicetemp";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        String itemno = rs.getString("itemno");
        String invdate = rs.getString("invdate");
        System.out.println("item:"+ itemno + ", date :"+invdate);
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  
}
