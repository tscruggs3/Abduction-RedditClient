/*
Button upvote = new Button();
Image upvoteImage = new Image(getClass().getResourceAsStream("images/upvote.png"));
upvote.setGraphic(new ImageView(upvoteImage));
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

public class PostGUI {

    private static final double SCENE_WIDTH = 1020;
    private static final double SCENE_HEIGHT = 765;
    private static final double MIN_UPVOTE_WIDTH = 30;
    private static final double MIN_TITLE_WIDTH = 250;
    private RedditController controller;

    public PostGUI(RedditController controller) {
        this.controller = controller;
    }

    public Scene getScene(Post post) {
        BorderPane page = new BorderPane();
        /* Top: Back button, subreddit link, user link
         * Right: Sidebar content
         * Center: VBox of comments
         */

        Pane sidebar = buildSidebar(post);
        Pane title = buildTitle(post);
        Pane comments = buildComments(post);

        BorderPane.setAlignment(sidebar, Pos.CENTER_RIGHT);

        page.setRight(sidebar);
        page.setTop(title);
        page.setCenter(comments);

        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    private int getParentCount(Comment comment) {
        int count = 0;
        Comment current = comment;
        while (current.getParent() != null) {
            current = comment.getParent();
            count += 1;
        }
        return count;
    }

    private Pane createCommentNode(Comment comment) {

       /* How this works:
        * All encompassed in a VBox
        *   Top Row: HBox
        *       Vote | Upvote | Downvote | author link
        *   Bottom Row: Text Label
        *       Content
        *
        * for each level of indent, add 30px padding to the left.
        *
        * For now, I'm just not gonna render anything past 7 levels.  This app isn't for /r/askouija
        */

        // THOMAS CODE //

        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(0, 5, 5, 5 + 50 * getParentCount(comment)));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        Button upvote = new Button("+");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(upvote, 1, 0);

        Text voteCount = new Text(Integer.toString(comment.getVotes()));
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

    private void recursiveIndex(Comment comment, ArrayList<Node> output) {
        output.add(createCommentNode(comment));
        for (Comment child : comment.getChildren()) {
            recursiveIndex(child, output);
        }
    }

    private Pane buildComments(Post post) {
        // Depth-first traversal of the tree.
        // Place everything in a vbox, then put that in the ScrollFrame
        // VBox.getChildren().add()

        ArrayList<Node> renderList = new ArrayList<Node>();

        for (Comment comment : post.getComments()) {
            recursiveIndex(comment, renderList);
        }

        VBox comments = new VBox();
        List children = comments.getChildren();

        for (Node comment : renderList) {
            children.add(comment);
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
        author.setOnAction(evt -> controller.requestPage("/u/janedoe"));
        author.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        HBox title = new HBox(5,back, subredditName, postName, author);
        title.setAlignment(Pos.BOTTOM_LEFT);
        title.setPadding(new Insets(0,0,20,0));
        return title;
    }

    private Pane buildSidebar(Post post) {
        WebView sidebarContent = new WebView();
        sidebarContent.setMaxWidth(200);
        sidebarContent.getEngine().loadContent(post.getSubreddit().getSidebarContent());
        FlowPane bin = new FlowPane(sidebarContent);
        bin.setMaxWidth(200);
        return bin;
    }

}
