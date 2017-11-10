import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.List;

public class PostGUI {

    public Scene render(Post post) {
        BorderPane page = new BorderPane();

        /* Top: Back button, subreddit link, user link
         * Right: Sidebar content
         * Center: VBox of comments
         */

        page.setRight(buildSidebar(post));
        page.setTop(buildTitle(post));
        page.setCenter(buildComments(post));

        Scene scene = new Scene(page, 600, 800);
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

    private Node createCommentNode(Comment comment) {

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

        // Top Row
        Text voteCount = new Text(Integer.toString(comment.getVotes()));
        Button upvote = new Button();
        Image upvoteImage = new Image(getClass().getResourceAsStream("images/upvote.png"));
        upvote.setGraphic(new ImageView(upvoteImage));
        Button downvote = new Button();
        Image downvoteImage = new Image(getClass().getResourceAsStream("images/downvote.png"));
        downvote.setGraphic(new ImageView(downvoteImage));
        Hyperlink author = new Hyperlink(comment.getUsername());
        HBox details = new HBox(10,voteCount, upvote, downvote, author);

        // Bottom Row
        Text content = new Text(comment.getContent());

        // Put it all together
        VBox frame = new VBox(10,details,content);
        frame.setPadding(new Insets(0, 5, 5, 5 + 30 * getParentCount(comment)));

        return frame;
    }

    private void recursiveIndex(Comment comment, ArrayList<Node> output) {
        output.add(createCommentNode(comment));
        for (Comment child : comment.getChildren()) {
            recursiveIndex(child, output);
        }
    }

    private Node buildComments(Post post) {
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

    private Node buildTitle(Post post) {
        // Back button, subreddit text, postname by text, author hyperlink
        Button back = new Button();
        Image backImage = new Image(getClass().getResourceAsStream("images/back.png"));
        back.setGraphic(new ImageView(backImage));
        Text subredditName = new Text(post.getSubreddit().getTitle()+"/ ");
        Text postName = new Text(post.getTitle() + "by ");
        Hyperlink author = new Hyperlink(post.getUsername());
        HBox title = new HBox(5,back, subredditName, postName, author);
        return title;
    }

    private Node buildSidebar(Post post) {
        WebView sidebarContent = new WebView();
        sidebarContent.getEngine().loadContent(post.getSubreddit().getSidebarContent());
        return sidebarContent;
    }

}
