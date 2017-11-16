import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;


/**
 * @author Thomas Scruggs
 *
 * A class that creates the GUI for the subreddit view
 */

public class SubredditGUI implements SceneRender {

    private RedditController controller;

    /** Creates a scene object.
     *
     * @param subreddit is a Subreddit object.
     * @return a scene given the subreddit.
     */
    public static Scene getScene(Subreddit subreddit, int position){

        HBox subredditTitle = addSubredditTitle(subreddit.getTitle());
        HBox menuPane = addMenus();
        VBox postsPane = addPosts(subreddit.getPostList());
        Text copyrightInfo = new Text("Reddit is a registered trademark of Reddit Inc.");
        Button nextPage = new Button("Next Page");
        nextPage.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/"+subreddit.getTitle(),position + 25));
        Button previousPage = new Button("Previous Page");
        if (position > 25) {
            previousPage.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/" + subreddit.getTitle(), position - 25));
        }
        HBox bottomMenu = new HBox(previousPage, nextPage);
        bottomMenu.setSpacing(10);
        bottomMenu.setAlignment(Pos.CENTER);
        postsPane.getChildren().add(bottomMenu);

        ScrollPane postScroller = new ScrollPane(postsPane);
        postScroller.setFitToWidth(true);

        VBox header = new VBox();
        header.getChildren().addAll(subredditTitle, menuPane);

        Scene scene = new Scene(new BorderPane(postScroller, header, null, copyrightInfo, null), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    private static HBox addSubredditTitle(String title) {
        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.TOP_LEFT);

        Text heading = new Text(title);
        heading.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, TITLE_SIZE));
        titlePane.getChildren().add(heading);

        return titlePane;

    }

    private static HBox addMenus() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);

        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(SCENE_WIDTH);

        Menu refreshMenu = new Menu("Refresh");

        /* TODO: Code to refresh to the page when clicked

        refreshMenuITEM.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //refresh the page
            }
        });
        */

        Menu randomMenu = new Menu("Random");
        randomMenu.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/random",0));

        /* TODO: Take the user to a random post when clicked

            randomMenuITEM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status.setText("Nooooo, we're not finished yet!");
            }
        });
        */

        Menu newSub = new Menu("Find Subreddit");

        menuBar.getMenus().addAll(refreshMenu, randomMenu, newSub);
        pane.getChildren().add(menuBar);

        return pane;
    }

    private static VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        int numbPosts = postList.size();

        for (int i = 0; i < numbPosts; i ++){
            Node post = SceneRender.buildPostPreview(i+1, postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }

}
