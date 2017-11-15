import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for generating JavaFX scenes from scraped reddit post data
 *
 * @author Jordan Sybesma
 */

public class PostGUI {

    private static final double SCENE_WIDTH = 1020;
    private static final double SCENE_HEIGHT = 765;
    private static final double MIN_UPVOTE_WIDTH = 30;
    private static final double MIN_TITLE_WIDTH = 250;
    private RedditController controller;

    /**
     * Constructor
     * @param controller The controlling class that observes events triggered on Post pages
     */
    public PostGUI(RedditController controller) {
        this.controller = controller;
    }

    /**
     * Returns a scene given scraped post data
     * @param post Scraped post data
     * @return Scene
     */
    public Scene getScene(Post post) {
        Pane page = new VBox();
        Pane title = buildTitle(post);
        System.out.println("Built title!");
        Pane comments = buildComments(post);
        System.out.println("Built comments!!");

        page.getChildren().addAll(title,comments);

        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        System.out.println("Returning rendered scene!");
        return scene;
    }

    // Helper method for comment recursion
    private int getParentCount(Comment comment) {
        int count = 0;
        Comment current = comment;
        while (current.getParent() != null) {
            current = comment.getParent();
            count += 1;
        }
        return count;
    }

    // Comment recursion
    private void recursiveIndex(Comment comment, ArrayList<Node> output) {
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

    private Pane createCommentNode(Comment comment) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(0, 5, 5, 5 + 50 * getParentCount(comment)));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        Button upvote = new Button("+");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(upvote, 1, 0);

        Text voteCount = new Text(comment.getVotes());
        voteCount.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(voteCount, HPos.CENTER);
        postPane.add(voteCount, 1, 1);

        Text content = new Text(comment.getContent());
        postPane.add(content, 2, 1);

        Text username = new Text(comment.getUsername());
        postPane.add(username, 2, 0);

        Button downvote = new Button("-");
        downvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(downvote, 1, 2);

        /*
        TODO: Implement outlines

        Rectangle outline = new Rectangle();
        outline.setX(0);
        outline.setY(0);
        outline.setHeight(100);
        outline.setWidth(200);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(2);
        outline.setArcHeight(20);
        outline.setArcWidth(20);
        */

        StackPane result = new StackPane(postPane);
        result.setAlignment(Pos.CENTER_LEFT);

        return result;
    }

    private Pane buildComments(Post post) {
        // Depth-first traversal of the comment tree.

        ArrayList<Node> renderList = new ArrayList<Node>();

        List<Comment> commentList = post.getRoot().getChildren();
        System.out.println("Top Level Comments: " + commentList.size());
        for (int i = 0; i < commentList.size(); i++) {
            recursiveIndex(commentList.get(i), renderList);
            System.out.println("Iterated " + i + "Times");
        }
        System.out.println("Got comments.");

        VBox comments = new VBox();
        List children = comments.getChildren();

        for (int i = 0; i < renderList.size(); i++) {
            children.add(renderList.get(i));
        }

        return comments;
    }

    private Pane buildTitle(Post post) {
        // Back button, subreddit text, postname by text, author hyperlink
        Button back = new Button();
        Image backImage = new Image(getClass().getResourceAsStream("images/back.png"));
        ImageView processedImage = new ImageView(backImage);
        back.setOnAction(evt -> controller.requestBack());
        processedImage.setFitHeight(75);
        processedImage.setFitWidth(75);
        back.setGraphic(processedImage);

        Text subredditName = new Text(post.getSubreddit().getTitle()+"/ ");
        subredditName.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        Text postName = new Text(post.getTitle() + " by");
        postName.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        Hyperlink author = new Hyperlink(post.getUsername());
        author.setOnAction(evt -> controller.requestUserPage("reddit.com/user/"+ post.getUsername()));
        author.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        HBox title = new HBox(5,back, subredditName, postName, author);
        title.setAlignment(Pos.BOTTOM_LEFT);
        title.setPadding(new Insets(0,0,20,0));
        return title;
    }

}
