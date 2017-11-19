import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public interface SceneRender {

    double SCENE_WIDTH = 1020;
    double SCENE_HEIGHT = 765;
    double MIN_TITLE_WIDTH = 250;
    double TITLE_SIZE = 30;
    double POST_TITLE_SIZE = 18;
    double USERNAME_SIZE = 40;
    String FONT_TYPE_TITLE = "Tahoma";
    String FONT_TYPE_CONTENT = "Verdana";
    Color POST_COLOR = Color.DARKGOLDENROD;
    Color USER_COLOR = Color.ROYALBLUE;

    static Node buildPostPreview(PostPreview postPreview) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(10,10,10,10));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        Text voteCount = new Text("         " + postPreview.getVote());
        voteCount.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        postPane.add(voteCount, 1, 1);

        Hyperlink postContent = new Hyperlink(postPreview.getTitle());
        postContent.setMinWidth(MIN_TITLE_WIDTH);
        postContent.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        postContent.setTextFill(POST_COLOR);
        postContent.setOnAction(evt -> RedditController.requestContentPage(postPreview.getContentURL(), postPreview.getTitle()));
        postPane.add(postContent, 2, 1);

        String userEntry = "By " + postPreview.getUsername();
        Hyperlink username = new Hyperlink(userEntry);
        username.setMinWidth(MIN_TITLE_WIDTH);
        username.setFont(Font.font(FONT_TYPE_CONTENT, POST_TITLE_SIZE/(1.2)));
        username.setTextFill(USER_COLOR);
        username.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ postPreview.getUsername(),"posts"));
        postPane.add(username, 3, 1);

        Hyperlink comments = new Hyperlink(postPreview.getNumComments());
        comments.setMinWidth(30);
        comments.setFont(Font.font(FONT_TYPE_CONTENT, POST_TITLE_SIZE/(1.5)));
        comments.setOnAction(evt -> RedditController.requestPostPage(postPreview.getCommentURL()));
        postPane.add(comments, 2, 2);

        return postPane;
    }

    //Helper method to create the scheme of the displayed comment
    //shows relevant info such as up/down cote, main content
    static Node buildCommentPreview(CommentPreview commentPreview) {
        GridPane commentPane = new GridPane();
        commentPane.setPadding(new Insets(10,10,10,65));
        commentPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        commentPane.setVgap(0);
        commentPane.setHgap(5);

        Label voteCount = new Label(commentPreview.getVote());
        voteCount.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        commentPane.add(voteCount, 1, 1);
        commentPane.setHalignment(voteCount, HPos.CENTER);

        Hyperlink commentTitle = new Hyperlink(commentPreview.getTitle());
        commentTitle.setMinWidth(MIN_TITLE_WIDTH/2);
        commentTitle.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        commentTitle.setTextFill(POST_COLOR);
        commentTitle.setOnAction(evt -> RedditController.requestPostPage(commentPreview.getOriginalPostUrl()));
        commentPane.add(commentTitle, 2, 1);

        Text commentContent = new Text(commentPreview.getComment());
        commentContent.setWrappingWidth(SCENE_WIDTH/1.5);
        commentPane.add(commentContent, 2, 2);


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

    static HBox addTitle(String title) {
        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.TOP_LEFT);

        Text heading = new Text(title);
        heading.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, TITLE_SIZE));
        titlePane.getChildren().add(heading);

        return titlePane;

    }

    static String filterText(String toFilter) {
        String filtered = toFilter.replaceAll("\\<.*?>","");
        filtered = filtered.replaceAll("&#39;","'");
        filtered = filtered.replaceAll("&quot;","\"");
        filtered = filtered.replaceAll("&bull;","*");
        return filtered;
    }

}
