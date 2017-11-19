import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
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
        Node content = buildContent(post);
        ScrollPane comments = buildComments(post);
        page.getChildren().addAll(title,content,comments);
        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);

        return scene;
    }

    // Helper method for comment recursion
    private static int getParentCount(Comment comment) {
        int count = 0;
        Comment current = comment;
        while (current.isRoot() == false) {
            current = current.getParent();

            count += 1;
        }
        return count;
    }

    // Comment recursion
    private static void recursiveIndex(Comment comment, ArrayList<Node> output) {
        output.add(createCommentNode(comment));
        List<Comment> children = comment.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Comment child = children.get(i);
            recursiveIndex(child,output);
        }
    }

    private static Pane createCommentNode(Comment comment) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(0, 5, 5, 5 + 50 * getParentCount(comment)));
        postPane.setMaxSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);

        Text voteCount = new Text(comment.getVotes());
        voteCount.setTextAlignment(TextAlignment.CENTER);
        voteCount.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        postPane.add(voteCount, 0,0);
        postPane.setHalignment(voteCount, HPos.RIGHT);
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setMaxWidth(60);
        col0.setMinWidth(60);

        Separator separateVotesAndUser = new Separator();
        separateVotesAndUser.setOrientation(Orientation.VERTICAL);
        postPane.add(separateVotesAndUser, 1,0);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(5);
        col1.setMinWidth(5);

        Hyperlink username = new Hyperlink(comment.getUsername());
        username.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        username.setTextFill(USER_COLOR);

        username.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ comment.getUsername(), "posts"));
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

        postPane.getColumnConstraints().addAll(col0,col1,col2,col3);

        return postPane;
    }

    private static ScrollPane buildComments(Post post) {
        // Depth-first traversal of the comment tree.

        ArrayList<Node> renderList = new ArrayList<Node>();

        List<Comment> commentList = post.getRoot().getChildren();
        for (int i = 0; i < commentList.size(); i++) {
            recursiveIndex(commentList.get(i), renderList);
        }

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
        Button back = SceneRender.buildBackButton();

        Text subredditName = new Text(post.getSubreddit() +"/ ");
        subredditName.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, TITLE_SIZE));

        Text postName = new Text(post.getTitle());
        postName.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, POST_TITLE_SIZE));
        postName.setWrappingWidth(SCENE_WIDTH / 3);

        Hyperlink author = new Hyperlink("by " + post.getUsername());
        author.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, POST_TITLE_SIZE));
        author.setTextFill(USER_COLOR);
        author.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ post.getUsername(),"posts"));
        author.setMaxWidth(200);


        HBox title = new HBox(back, subredditName,postName,author);
        title.setSpacing(20);

        title.setAlignment(Pos.CENTER_LEFT);

        return title;
    }

    private static Node buildContent(Post post) {
        Separator separateTop = new Separator();
        separateTop.setOrientation(Orientation.HORIZONTAL);

        Text content = new Text(post.getContent());
        content.setTextAlignment(TextAlignment.CENTER);
        content.setWrappingWidth(SCENE_WIDTH - 200);
        content.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.NORMAL, POST_TITLE_SIZE));

        Separator separateBottom = new Separator();
        separateBottom.setOrientation(Orientation.HORIZONTAL);

        VBox container = new VBox(separateTop, content, separateBottom);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return container;
    }

}
