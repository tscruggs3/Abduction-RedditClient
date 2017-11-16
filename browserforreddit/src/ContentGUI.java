import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

        Button back = new Button();
        Image backImage = new Image(PostGUI.class.getResourceAsStream("images/back.png"));
        ImageView processedImage = new ImageView(backImage);
        back.setOnAction(evt -> RedditController.requestBack());
        processedImage.setFitHeight(75);
        processedImage.setFitWidth(75);
        back.setGraphic(processedImage);

        Text titleLabel = new Text(title);
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        topBar.getChildren().addAll(back, titleLabel);
        page.getChildren().addAll(topBar, pageContent);
        page.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

}
