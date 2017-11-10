import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.List;


/**
 * @author Thomas Scruggs
 *
 * A class that creates the GUI for the subreddit view
 */

public class SubredditGUI {
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 400;
    private static final double MIN_UPVOTE_WIDTH = 110;
    private static final double MIN_TITLE_WIDTH = 250;
    private static final int POSTS_PER_PAGE = 10;


    public Scene getScene(Subreddit subreddit){

        HBox subredditTitle = addSubredditTitle(subreddit.getTitle());
        HBox menuPane = addMenus();
        VBox postsPane = addPosts(subreddit.getPostList());
        Text copyrightInfo = new Text("Reddit is a registered trademark of Reddit Inc.");

        ScrollPane postScroller = new ScrollPane(postsPane);
        postScroller.setFitToWidth(true);

        VBox header = new VBox();
        header.getChildren().addAll(subredditTitle, menuPane);

        Scene scene = new Scene(new BorderPane(postScroller, header, null, copyrightInfo, null), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    private HBox addSubredditTitle(String title) {
        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.TOP_LEFT);

        Text heading = new Text(title);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
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

    private VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        int numbPosts = Math.min(POSTS_PER_PAGE, postList.size());

        for (int i = 0; i < numbPosts; i ++){
            GridPane post = addPostPreview(i+1, postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }

    private GridPane addPostPreview(int postNumber, PostPreview postPreview) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(10,10,10,10));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        /* TODO: I think this is the something close to the code for making the images buttons.

        Image backImage = new Image(getClass().getResourceAsStream("back.png"));
        Button upvote = new Button("", new ImageView(backImage));
         */
        Button upvote = new Button("Upvote");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(upvote, 1, 0);

        Text postNumb = new Text(Integer.toString(postNumber));
        postPane.add(postNumb, 0, 1);


        Text voteCount = new Text("         " + Integer.toString(postPreview.getVote()));
        postPane.add(voteCount, 1, 1);

        upvote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               upvote.setText("Upvoted!");
               upvote.setTextFill(Color.GREEN);
               //TODO: tell controller to increase vote by 1
                voteCount.setText("         " + Integer.toString(postPreview.getVote()));
            }
        });


        Button postContent = new Button(postPreview.getTitle());
        postContent.setMinWidth(MIN_TITLE_WIDTH);
        postContent.setOnAction(evt -> postContent.setText("Clicked on " + postPreview.getContentURL()));
        postPane.add(postContent, 2, 1);

        String userEntry = "By " + postPreview.getUsername();
        Button username = new Button(userEntry);
        username.setMinWidth(MIN_TITLE_WIDTH);
        username.setOnAction(evt -> username.setText("Clicked on " + postPreview.getUsername()));
        postPane.add(username, 3, 1);

        Button downvote = new Button("downvote");
        downvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(downvote, 1, 2);

        downvote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                downvote.setText("Downvoted!");
                downvote.setTextFill(Color.RED);
                //TODO: tell controller to decrease vote by 1
                voteCount.setText("         " + Integer.toString(postPreview.getVote()));
            }
        });

        Button comments = new Button("Comments");
        comments.setMinWidth(MIN_UPVOTE_WIDTH);
        /* TODO on click, the comments button should go to the comments page

         */
        comments.setOnAction(evt -> comments.setText("Clicked on " + postPreview.getCommentURL()));
        postPane.add(comments, 3, 2);

        return postPane;
    }

}
