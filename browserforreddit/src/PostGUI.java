import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for generating JavaFX scenes from scraped reddit post data
 *
 * @author Jordan Sybesma
 */

public class PostGUI implements SceneRender {

    /**
     * Returns a scene given scraped post data
     * @param post Scraped post data
     * @return Scene
     */
    public static Scene getScene(Post post) {
        Pane page = new VBox();
        Pane title = buildTitle(post);

        System.out.println("Built title!");
        Node content = buildContent(post);

        ScrollPane comments = buildComments(post);
        System.out.println("Built comments!!");

        page.getChildren().addAll(title,content,comments);

        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        System.out.println("Returning rendered scene!");
        return scene;
    }

    // Helper method for comment recursion
    private static int getParentCount(Comment comment) {
        int count = 0;
        Comment current = comment;
        while (current.getParent() != null) {
            current = comment.getParent();
            count += 1;
        }
        return count;
    }

    private static void addTopLevel(Comment comment, ArrayList<Node> output) {
        for (Comment child: comment.getChildren()) {
            output.add(createCommentNode(child));
        }
    }

    // Comment recursion
    private static void recursiveIndex(Comment comment, ArrayList<Node> output) {
        output.add(createCommentNode(comment));
        System.out.println(comment.getUsername() + " " + comment.getVotes());
        List<Comment> children = comment.getChildren();
        System.out.println(children.size());
        for (int i = 0; i < children.size(); i++) {
            Comment child = children.get(i);
            recursiveIndex(child,output);
            System.out.println(child.getUsername() + " " + child.getVotes());

        }
        System.out.println("Done iterating over comments!");
    }

    private static Pane createCommentNode(Comment comment) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(0, 5, 5, 5 + 50 * getParentCount(comment)));
        postPane.setMaxSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);

        Text voteCount = new Text(comment.getVotes());
        voteCount.setTextAlignment(TextAlignment.CENTER);
        postPane.add(voteCount, 0,0);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setMaxWidth(30);
        col0.setMinWidth(30);

        Separator separateVotesAndUser = new Separator();
        separateVotesAndUser.setOrientation(Orientation.VERTICAL);
        postPane.add(separateVotesAndUser, 1,0);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(5);
        col1.setMinWidth(5);


        Hyperlink username = new Hyperlink(comment.getUsername());
        username.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ comment.getUsername()));
        postPane.add(username, 2,0);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(200);
        col2.setMinWidth(200);

        Separator separateUserAndContent = new Separator();
        separateUserAndContent.setOrientation(Orientation.VERTICAL);
        postPane.add(separateUserAndContent, 1,1);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMaxWidth(5);
        col3.setMinWidth(5);

        Text content = new Text(comment.getContent());
        content.setWrappingWidth(SCENE_WIDTH/3);
        postPane.add(content, 2,1);

        /*
        Rectangle outline = new Rectangle();
        outline.setX(50 * getParentCount(comment));
        outline.setY(0);
        outline.setHeight(postPane.getPrefHeight());
        outline.setWidth(SCENE_WIDTH / 2);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(2);
        outline.setArcHeight(20);
        outline.setArcWidth(20);
        */

        postPane.getColumnConstraints().addAll(col0,col1,col2,col3);

        StackPane result = new StackPane(postPane);
        result.setAlignment(Pos.CENTER_LEFT);

        return result;
    }

    private static ScrollPane buildComments(Post post) {
        // Depth-first traversal of the comment tree.

        ArrayList<Node> renderList = new ArrayList<Node>();

        /*
        List<Comment> commentList = post.getRoot().getChildren();
        System.out.println("Top Level Comments: " + commentList.size());
        for (int i = 0; i < commentList.size(); i++) {
            recursiveIndex(commentList.get(i), renderList);
            System.out.println("Iterated " + i + "Times");
        }
        */
        addTopLevel(post.getRoot(), renderList);

        VBox comments = new VBox();
        List children = comments.getChildren();

        for (int i = 0; i < renderList.size(); i++) {
            children.add(renderList.get(i));
        }

        ScrollPane window = new ScrollPane(comments);
        window.setFitToWidth(true);

        return window;
    }

    private static Pane buildTitle(Post post) {
        // Back button, subreddit text, postname by text, author hyperlink
        Button back = new Button();
        Image backImage = new Image(PostGUI.class.getResourceAsStream("images/back.png"));
        ImageView processedImage = new ImageView(backImage);
        back.setOnAction(evt -> RedditController.requestBack());
        processedImage.setFitHeight(75);
        processedImage.setFitWidth(75);
        back.setGraphic(processedImage);

        String heading = post.getSubreddit().getTitle()+"/ ";
        Text subredditName = SceneRender.buildHeader(heading, "Title");

        String postText = post.getTitle();
        Text postName = SceneRender.buildHeader(postText, "Post Title");
        postName.setWrappingWidth(SCENE_WIDTH / 3);

        Hyperlink author = new Hyperlink("by " + post.getUsername());
        author.setOnAction(evt -> RedditController.requestUserPage("reddit.com/user/"+ post.getUsername()));
        author.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        author.setMaxWidth(200);

        HBox title = new HBox(back, subredditName,postName,author);

        title.setAlignment(Pos.CENTER_LEFT);
        return title;
    }

    private static Node buildContent(Post post) {
        Text content = new Text(post.getContent());
        return content;
    }

}
