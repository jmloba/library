
package TempInvoiceAdd;

import addmember.AddMemberController;
import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import fds.fdBrand;
import generalRoutines.ProgramRoutines;
import generalRoutines.myfunction;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import listbook.BookListController;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class TempInvoiceAddController implements Initializable {

  
  @FXML  private HBox tf_date;
  
  @FXML  private TextField tf_itemnumber;
  @FXML  private TextArea ta_desc;
  @FXML  private ComboBox<String> cb_brand;
  @FXML  private ComboBox<String> cb_category;
  @FXML  private TextField tf_quantity;
  @FXML  private TextField tf_price;
    @FXML  private TextField tf_amount;
  
  @FXML  private Button btn_save;
  @FXML  private Button btn_cancel;
  @FXML  private AnchorPane my_anchorpane;
  Connection connew = null;
  
  static DatabaseHandler  handler;
  static boolean showmessage;
  
  @FXML
  private DatePicker td_date;

ObservableList list_data = FXCollections.observableArrayList();
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
    DatabaseConnection connect = new DatabaseConnection();
    Common_Var.connew = connect.getConnection();
    Common_Var.showmessage = true;
    
    connew = Common_Var.connew ;
    showmessage = Common_Var.showmessage;
//    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);
            myfunction.numbersOnly(tf_quantity);
        myfunction.decimalNumberFormat(tf_price);
        myfunction.decimalNumberFormat(tf_amount);

    fill_combo();
    checkdata();
    initialize_values();
    
  }  

  @FXML
  private void routine_cancel(ActionEvent event) {
    Stage stage = (Stage) my_anchorpane.getScene().getWindow();
    stage.close();

  }

  @FXML
  private void routine_save(ActionEvent event) {
    
    LocalDate mdate = td_date.getValue();
    String item = tf_itemnumber.getText();
    String desc = ta_desc.getText(); 
    String brand = cb_brand.getValue();
    String category = cb_category.getValue();
    int quantity = Integer.valueOf(tf_quantity.getText());
    double price = Integer.valueOf(tf_price.getText());
    Double amount = Double.valueOf(tf_amount.getText());
    System.out.println(" date : "+ mdate+ "\n"+
            " item : "+ item+ "\n"+
            " desc : "+ desc+ "\n"+
            " brand : "+ brand+ "\n"+
            " category : "+ category+ "\n"+
            " quantity : "+ quantity+ "\n"+
            " price : "+ price+ "\n"+
            " amount : "+ amount
            );
    Boolean flag =  item.isEmpty() ||desc.isEmpty() ||brand.isEmpty()||category.isEmpty() || (quantity ==0 )|| (price == 0) ;
    if (flag){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      String alert_message ="All Entries are required...";
      alert.setContentText(alert_message);
      alert.showAndWait();
      return;
    } else{
      saveRecord(mdate,item, desc,brand,category,quantity,price,amount);
    }
    
   
  }
private void saveRecord(
        LocalDate invdate,String item, String desc, 
        String brand,   String category, 
        int quantity, double price,double amount) {
  
     String bcode =  ProgramRoutines.get_BrandCode(brand);
     String ccode =  ProgramRoutines.get_CategoryCode(category);
     
     String query="Insert into invoicetemp "
      + "(invdate, itemno,  description, "
      + "brandcode,categorycode, quantity, price ,amount) "
      + " values (" 
      + "'"+  invdate    + "',"
      + "'"+  item    + "',"
      + "'"+  desc    + "',"             
      + "'"+  bcode    + "',"             
      + "'"+  ccode    + "',"             
      +  quantity    + ","             
      +  price       + ","             
      +  amount    +  ")";
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
    String query = "Select invdate from invoicetemp";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        
        String invdate = rs.getString("invdate");
        
        showmessages.displayMessage("date :"+ invdate);
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void fill_combo() {
    fill_combobrand();
    fill_categorybrand();
  }

  private void fill_combobrand() {
    list_data = ProgramRoutines.getForComboBrand();
    cb_brand.setItems(list_data);
  }

  private void fill_categorybrand() {
    list_data = ProgramRoutines.getForComboCategory();
    cb_category.setItems(list_data);
  }


  private void get_amount_price(ActionEvent event) {
    int quantity = Integer.valueOf(tf_quantity.getText());
    double price = Integer.valueOf(tf_price.getText());
    compute_value(quantity,price);
  }

  private void get_amount_amount(ActionEvent event) {
    int quantity = Integer.valueOf(tf_quantity.getText());
    double price = Integer.valueOf(tf_price.getText());
    
    compute_value(quantity,price);
  }

  private void compute_value(int quantity, double price) {
    Double amount = quantity * price;
    tf_amount.setText(amount.toString());
    
    
    
  }

  private void initialize_values() {
    tf_quantity.setText("0");
    tf_amount.setText("0");
    tf_price.setText("0");
  }

  
  
  
  
}

