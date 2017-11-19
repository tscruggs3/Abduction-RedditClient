import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StartScreenGUI implements SceneRender {
   
   /* Creates the starting Scene for when the application launches.
    * @return the Scene that displays when lauching the application
    */
   public static Scene getScene(){
      BorderPane root = new BorderPane();
      root.setPrefSize(SCENE_WIDTH,SCENE_HEIGHT);
      
      Label programName = new Label("Abduction for Reddit");
      root.setAlignment(programName, Pos.Center);
      programName.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, USERNAME_SIZE));
      
      root.setCenter(programName);
      
      Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
      return scene;

   }
}