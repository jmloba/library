
package util;


import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryAssistantUtil {
  public static final String image_loc = "/resources/ic_launcher.png";
  public static void setStageIcon(Stage stage){
    stage.getIcons().add(new Image(image_loc));
  }
  
}
