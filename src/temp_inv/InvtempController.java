/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp_inv;

import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import fds.fdInvoiceTemp;
import generalRoutines.ProgramRoutines;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import listbook.BookListController;
import generalRoutines.viewCurrentConnection;
import java.sql.PreparedStatement;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class InvtempController implements Initializable {

 @FXML  private TableView<fdInvoiceTemp> table_view;

 @FXML  private TableColumn<fdInvoiceTemp, Integer> col_id;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_itemno;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_date;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_desc;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_brandcode;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_categorycode;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_quantity;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_price;
 @FXML  private TableColumn<fdInvoiceTemp, String> col_amount;
 @FXML  private TableColumn<fdInvoiceTemp, Button> col_delete;
 
  
  Connection connew = null;
  static DatabaseHandler  handler;
  ObservableList<fdInvoiceTemp> list = FXCollections.observableArrayList();
  


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;

    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("temp_inv","Invoice Temp List ", connew);
    init_column();
    loadData();
  }  
  private void init_column() {
      // fields from fd
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    
    col_itemno.setCellValueFactory(new PropertyValueFactory<>("itemno"));
    
    col_date.setCellValueFactory(new PropertyValueFactory<>("invdate"));
    col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

    col_brandcode.setCellValueFactory(new PropertyValueFactory<>("brand_code"));
    col_categorycode.setCellValueFactory(new PropertyValueFactory<>("category_code"));
    
    col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    col_delete.setCellValueFactory(new PropertyValueFactory<>("del_button"));
  }

  private void loadData() {
    
String query = "Select * from invoicetemp";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id          = rs.getInt("id");
        String invdate  = rs.getString("invdate");
        
        String itemno   = rs.getString("itemno");
        
        String description   = rs.getString("description");
        String brandcode     = rs.getString("brandcode");
        String categorycode  = rs.getString("categorycode");

        int quantity   = rs.getInt("quantity");
        Double price   = rs.getDouble("price");
        Double amount  = rs.getDouble("amount");
        
        Button btndelete = new Button();
          btndelete.setText("Delete");
          btndelete.setOnAction(e -> {  // delete button
          deletefrom_InvoiceTemp(id);
          list.clear();
          loadData();
          
        });
        
        list.add(new fdInvoiceTemp(id, invdate, itemno, 
                 description,
                brandcode, categorycode, 
                quantity, price, amount, btndelete));
      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_view.getItems().setAll(list);
  }

  private void deletefrom_InvoiceTemp(int id) {
   try {
     PreparedStatement myStmt_deleterec = null;
     String mysql_dele = "delete from "
             + "invoicetemp "
             + "where id = " + id;
     
     myStmt_deleterec = connew.prepareStatement(mysql_dele);
     myStmt_deleterec.executeUpdate();
   } catch (SQLException ex) {
     Logger.getLogger(InvtempController.class.getName()).log(Level.SEVERE, null, ex);
   }
  }
}