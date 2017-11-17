import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * @author Thomas Scruggs
 *
 * A class that creates the GUI for the search page view
 */

public class SearchPageGUI implements SceneRender {

    private RedditController controller;

    /**
     * Creates a scene object.
     *
     * @return a scene given the subreddit.
     */
    public static Scene getScene() {

        HBox searchTitle = SceneRender.addTitle("Search for a Subreddit");
        Text copyrightInfo = new Text("Reddit is a registered trademark of Reddit Inc.");
        HBox menuPane = addMenus();

        HBox searchContent = createSearchContent();

        VBox header = new VBox();
        header.getChildren().addAll(searchTitle, menuPane);

        Scene scene = new Scene(new BorderPane(searchContent, header, null, copyrightInfo, null), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    private static HBox addMenus() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setMinWidth(SCENE_WIDTH);

        Button back = new Button("Back");
        back.setOnAction(evt -> RedditController.requestBack());

        pane.getChildren().add(back);

        return pane;
    }

    private static HBox createSearchContent() {
        HBox searchContent = new HBox();
        VBox searchBox = new VBox();
        VBox suggestedLinks = new VBox();

        return searchContent;
    }
}