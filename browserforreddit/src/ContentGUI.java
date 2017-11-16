import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class ContentGUI implements SceneRender {

    public static Scene getScene(String url, String title) {
        VBox page = new VBox();
        // Add content
        WebView pageContent = new WebView();
        pageContent.setMaxWidth(SCENE_WIDTH);
        pageContent.setMaxHeight(SCENE_HEIGHT - 75);
        pageContent.getEngine().load(url);

        // Add back and post title button on top
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);

        Button back = SceneRender.buildBackButton();

        Text titleLabel = new Text(title);
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        topBar.getChildren().addAll(back, titleLabel);
        page.getChildren().addAll(topBar, pageContent);
        page.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

}
