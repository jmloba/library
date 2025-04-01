
 
package CategoryList;

import database.Common_Var;
import database.DatabaseHandler;
import fds.fdBrand;
import fds.fdCategory;
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

public class CategoryListController implements Initializable {

  @FXML
  private TableView<fdCategory> table_View;
  @FXML
  private TableColumn<fdCategory, Integer> col_id;
  @FXML
  private TableColumn<fdCategory, String> col_categorycode;
  @FXML
  private TableColumn<fdCategory, String> col_categorydesc;
  
  ObservableList<fdCategory> list = FXCollections.observableArrayList();
  static DatabaseHandler handler;
  Connection connew = null;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
//     DatabaseConnection connect = new DatabaseConnection();
//    Common_Var.connew = connect.getConnection();
    connew =Common_Var.connew;
    boolean showmessage = Common_Var.showmessage;
    handler = DatabaseHandler.getInstance() ;
    viewCurrentConnection.showCurrentConnection("CategoryList", "List View Category", connew);

    init_column();
    loadData();
  }  
    private void init_column() {
      
//      columns from fdBrand
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_categorycode.setCellValueFactory(new PropertyValueFactory<>("code"));
    col_categorydesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
    
  }
    
  private void loadData() {
    String query = "Select * from categ";
    ResultSet rs = handler.execQuery( query);
    try {
      while(rs.next()){
        int id = rs.getInt("id");
        String code = rs.getString("categorycode");
        String name = rs.getString("categoryname");
        
        list.add(new fdCategory(id,code,name));
        showmessages.displayMessage("id :"+ id +
                ",  categorycode:" + code+
                ",  name:" + name);
      }
    } catch (SQLException ex) {
      Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    table_View.getItems().setAll(list);
    
    
  } 
}
