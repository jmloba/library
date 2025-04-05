/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listmember;

import addbook.FXMLDocumentController;
import addmember.AddMemberController;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listbook.BookListController;
import util.LibraryAssistantUtil;

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
  DatabaseHandler handler;
  
  
  ObservableList<fdMember> list = FXCollections.observableArrayList();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    handler =  DatabaseHandler.getInstance();

    boolean showmessage = Common_Var.showmessage;
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
//    DatabaseHandler handler = new DatabaseHandler();
    list.clear();
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
    table_View.setItems(list);
    
    
  }

  @FXML
  private void handleMemberDeleteOption(ActionEvent event) {
    System.out.println("ready for deletion");
    
    fdMember selectedForDeletion  = table_View.getSelectionModel().getSelectedItem();
     if (selectedForDeletion== null){
        System.out.println("no item for deletion");
      AlertMaker.AlertMaker.showErrorMessage("No record selected", "please select record");
    } else{
       System.out.println("selected : "+selectedForDeletion.member_id);
//       check if there is borrowed gook by member

       if (!borrowed_books(selectedForDeletion.member_id)){
             delete_member(selectedForDeletion.member_id);
             loadData();
             
             
             
       } else{
          AlertMaker.AlertMaker.showSimpleAlert("Alert", "There  are borrowed books");
          
       }
       
     }
  }
    private boolean borrowed_books(String member_id) {
    try {
      Integer count = 0;
      String query = "Select * from bookissuelog where member_id = '"+member_id+"'";
      System.out.println("query borrowed books : "+ query);
      
      
      ResultSet rs = handler.execQuery( query);
      while(rs.next()){
        count ++;
       
      }
      if(count> 0){
        System.out.println("count borrowed books :" + count);
        return true;
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }



  private void delete_member(String member_id) {
String query = "delete from "
              + "member "
              + "where member_id = '" + member_id+"'";    
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
  private void handleMemberEditOption(ActionEvent event) {
     
    fdMember selectedForEdit = table_View.getSelectionModel().getSelectedItem();
    if (selectedForEdit == null) {
      AlertMaker.AlertMaker.showErrorMessage("No member is selected", "please select a member to edit");
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/addmember/addMember.fxml"));
      Parent parent = loader.load();

      // note : FXMLDocumentController  -> from addbook
      AddMemberController controller = (AddMemberController) loader.getController();
      
      controller.inflateUI(selectedForEdit);

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setTitle("Edit Member");
      stage.setScene(new Scene(parent));
      stage.show();
      LibraryAssistantUtil.setStageIcon(stage);
      
      stage.setOnCloseRequest((e)->{
        handleMemberRefreshOption(new ActionEvent());
      
      });
      
    } catch (IOException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @FXML
  private void handleMemberRefreshOption(ActionEvent event) {
    loadData();
  }


  
}
