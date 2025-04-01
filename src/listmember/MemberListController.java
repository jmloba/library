/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listmember;

import database.Common_Var;
import database.DatabaseConnection;
import database.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import fds.fdMember;
import generalRoutines.routineInitialization;
import generalRoutines.showmessages;
import generalRoutines.viewCurrentConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import listbook.BookListController;

/**
 * FXML Controller class
 *
 * @author JovenLoba
 */
public class MemberListController implements Initializable {

  @FXML
  private TableView<fdMember> table_View;
  @FXML
  private TableColumn<fdMember, Integer> col_id;
  @FXML
  private TableColumn<fdMember, String> col_memberid;
  @FXML
  private TableColumn<fdMember, String> col_name;
  @FXML
  private TableColumn<fdMember, String> col_mobile;
  @FXML
  private TableColumn<fdMember, String> col_email;
  
  Connection connew = null;
  static DatabaseHandler  handler;
  
  ObservableList<fdMember> list = FXCollections.observableArrayList();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    routineInitialization.allRoutineInitialization();
    init_column();
    loadData();
  }  
    private void init_column() {
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_memberid.setCellValueFactory(new PropertyValueFactory<>("member_id"));
    col_name.setCellValueFactory(new PropertyValueFactory<>("member_name"));
    col_mobile.setCellValueFactory(new PropertyValueFactory<>("member_mobile"));
    col_email.setCellValueFactory(new PropertyValueFactory<>("member_email"));
  }

  private void loadData() {
    String query = "Select * from member";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id = rs.getInt("id");
        String m_id       = rs.getString("member_id");
        String m_name     = rs.getString("member_name");
        String m_mobile   = rs.getString("member_mobile");
        String m_email    = rs.getString("member_email");
        
        

        list.add(new fdMember(id,m_id,m_name,m_mobile,m_email));
        showmessages.displayMessage("code :"+ id +
                ",  member id:" + m_id+
                ",  name:" + m_name+
                ",  mobile:" + m_mobile+
                ",  email:" + m_email);
      }
        
        
    } catch (SQLException ex) {
      Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.getItems().setAll(list);
    
    
  }
  
}
