/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrandList;

import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;
import fds.fdBrand;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;

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


/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class BrandlistController implements Initializable {
ObservableList<fdBrand> list = FXCollections.observableArrayList();
  @FXML
  private TableView<fdBrand> table_View;
  @FXML
  private TableColumn<fdBrand, Integer     > col_id;
  @FXML
  private TableColumn<fdBrand, String> col_brandcode;
  @FXML
  private TableColumn<fdBrand, String> col_brandname;
  
  static DatabaseHandler handler;
  Connection connew = null;

  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    handler = DatabaseHandler.getInstance() ;
    boolean showmessage = Common_Var.showmessage;
    viewCurrentConnection.showCurrentConnection("BrandList ", "Brand List", connew);
  
    init_column();
    loadData();
  }  
    private void init_column() {
      
//      columns from fdBrand
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_brandcode.setCellValueFactory(new PropertyValueFactory<>("brandcode"));
    col_brandname.setCellValueFactory(new PropertyValueFactory<>("brandname"));
    
  }
    
  private void loadData() {
    String query = "Select * from brand";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id = rs.getInt("id");
        String brandcode = rs.getString("brandcode");
        String brandname = rs.getString("brandname");
        
        list.add(new fdBrand(id,brandcode,brandname));
        showmessages.displayMessage("id :"+ id +  ",  brandcode:" + brandcode+
          ",  brandname:" + brandname);
      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.getItems().setAll(list);
    
    
  }
  
}
