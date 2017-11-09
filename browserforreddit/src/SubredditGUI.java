import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import javafx.scene.control.*;
import java.util.List;


/**
 * @author Thomas Scruggs
 *
 * A class that creates the GUI for the subreddit view
 */

public class SubredditGUI extends Application{
    private static final double SCENE_WIDTH = 500;
    private static final double SCENE_HEIGHT = 300;
    private static final double MIN_UPVOTE_WIDTH = 300;
    private static final double MIN_TITLE_WIDTH = 400;
    private static final double POSTS_PER_PAGE = 10;


    @Override
    public void start(Stage primaryStage, Subreddit subreddit){
        GridPane root = new GridPane();
        //Could make root a borderpane instead
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setMinSize(300, 300);
        root.setVgap(5);
        root.setHgap(5);

        HBox subredditTitle = addTitle(subreddit.getTitle());
        HBox menuPane = addMenus();
        VBox scrollBar = addScrollbar();
        VBox postsPane = addPosts(subreddit.getPostList());

        root.add(subredditTitle, 0, 0);
        root.add(scrollBar, 1, 0);
        root.add(menuPane, 0, 1);
        root.add(postsPane, 0, 2);
        Text copyrightInfo = new Text("Reddit is a trademark of Reddit");
        root.add(copyrightInfo, 0, 3);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("Abduction Reddit Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private HBox addTitle(String title) {
        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.TOP_LEFT);

        Text heading = new Text(title);
        titlePane.getChildren().add(heading);

        return titlePane;

    }

    private HBox addMenus() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);

        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(SCENE_WIDTH);

        Menu refreshMenu = new Menu("Refresh");

        /* TODO: Code to fresh to the page when clicked

        refreshMenuITEM.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //refresh the page
            }
        });
        */

        Menu sortMenu = new Menu("Sort by Popular V");

        /* TODO: Code sort by popular when clicked
        sortMenuITEM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //call sort method
            }
        });
         */

        Menu randomMenu = new Menu("Random");

        /* TODO: Take the user to a random post when clicked

            randomMenuITEM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                status.setText("Nooooo, we're not finished yet!");
            }
        });
        */



        menuBar.getMenus().addAll(refreshMenu, sortMenu, randomMenu);
        pane.getChildren().add(menuBar);

        return pane;
    }

    private VBox addScrollbar() {
    }

    private VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        for (int i = 0; i < POSTS_PER_PAGE; i ++){
            GridPane post = addPostPreview(i, postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }

    private GridPane addPostPreview(int postNumber, PostPreview postPreview) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(10,10,10,10));
        postPane.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        postPane.setVgap(5);
        postPane.setHgap(5);

        Button upvote = new Button("upvote");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(upvote, 1, 0);

        Text postNumb = new Text(Integer.toString(postNumber));
        postPane.add(postNumb, 0, 1);

        Text voteCount = new Text(Integer.toString(postPreview.getVote()));
        postPane.add(voteCount, 1, 1);

        Button postContent = new Button(postPreview.getTitle());
        postContent.setMinWidth(MIN_TITLE_WIDTH);
        postPane.add(postContent, 2, 1);

        String userEntry = "By" + postPreview.getUsername();
        Text username = new Text(userEntry);
        postPane.add(username, 3, 1);

        Button downvote = new Button("downvote");
        downvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(downvote, 1, 2);

        Button comments = new Button("Comments");
        comments.setMinWidth(MIN_UPVOTE_WIDTH);
        /* TODO on click, the comments button should go to the comments page

         */
        postPane.add(comments, 3, 2);

        return postPane;
    }

}
