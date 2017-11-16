import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public interface SceneRender {

    double SCENE_WIDTH = 1020;
    double SCENE_HEIGHT = 765;
    double MIN_TITLE_WIDTH = 250;
    double TITLE_SIZE = 30;
    double POST_TITLE_SIZE = 18;
    double USERNAME_SIZE = 60;
    String FONT_TYPE = "Verdana";


    static Node buildPostPreview(int postNumber, PostPreview postPreview) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(10,10,10,10));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        Text postNumb = new Text(Integer.toString(postNumber));
        postPane.add(postNumb, 0, 1);

        Text voteCount = new Text("         " + postPreview.getVote());
        postPane.add(voteCount, 1, 1);

        Hyperlink postContent = new Hyperlink(postPreview.getTitle());
        postContent.setMinWidth(MIN_TITLE_WIDTH);
        postContent.setOnAction(evt -> RedditController.requestContentPage(postPreview.getContentURL(), postPreview.getTitle()));
        postPane.add(postContent, 2, 1);

        String userEntry = "By " + postPreview.getUsername();
        Hyperlink username = new Hyperlink(userEntry);
        username.setMinWidth(MIN_TITLE_WIDTH);
        username.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ postPreview.getUsername(),"posts"));
        postPane.add(username, 3, 1);

        Hyperlink comments = new Hyperlink(postPreview.getNumComments());
        comments.setMinWidth(30);
        comments.setOnAction(evt -> RedditController.requestPostPage(postPreview.getCommentURL()));
        postPane.add(comments, 2, 2);

        return postPane;
    }

    //Helper method to create the scheme of the displayed comment
    //shows relevant info such as up/down cote, main content
    static Node buildCommentPreview(int commentNumber, CommentPreview commentPreview) {
        GridPane commentPane = new GridPane();
        commentPane.setPadding(new Insets(10,10,10,10));
        commentPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        commentPane.setVgap(0);
        commentPane.setHgap(5);

        Text commentNumb = new Text(Integer.toString(commentNumber));
        commentPane.add(commentNumb, 0, 1);

        Label voteCount = new Label(Integer.toString(commentPreview.getVote()));
        commentPane.add(voteCount, 1, 1);
        commentPane.setHalignment(voteCount, HPos.CENTER);

        Hyperlink commentContent = new Hyperlink(commentPreview.getTitle());
        commentContent.setMinWidth(MIN_TITLE_WIDTH/2);
        commentContent.setOnAction(evt -> RedditController.requestPostPage(commentPreview.getURL()));
        commentPane.add(commentContent, 2, 1);

        return commentPane;
    }


    static Button buildBackButton() {
        Button back = new Button();
        Image backImage = new Image(UserGUI.class.getResourceAsStream("images/back.png"));
        ImageView processedImage = new ImageView(backImage);
        back.setOnAction(evt -> RedditController.requestBack());
        processedImage.setFitHeight(75);
        processedImage.setFitWidth(75);
        back.setGraphic(processedImage);
        return back;
    }
}
