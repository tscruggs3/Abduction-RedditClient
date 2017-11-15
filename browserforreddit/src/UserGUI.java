import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.List;
import java.util.ArrayList;

public class UserGUI  {
    private static final double SCENE_WIDTH = 1020;
    private static final double SCENE_HEIGHT = 765;
    private static final double MIN_UPVOTE_WIDTH = 30;
    private static final double MIN_TITLE_WIDTH = 250;
    private static final double POSTS_PER_PAGE = 2;
    private RedditController controller;

   /**
    *Creates the controller for the UserGUI interface.
    *This method is called every time the UserGUI is created and used
    * so that the controller can see what buttons/links are clicked on and
    *display the appropiate information.
    *@param controller a controller object that display all appropiate information when asked for
    */
    public UserGUI(RedditController controller) {
        this.controller = controller;
    }
    
   /**
    *Creates the scene for a UserGUI page. This scene displays all posts and comments for the user. 
    *Also displays all relevant user information usch as CommentKarma, PostKarma, and links to all posts/comments
    *@param user a User object that is fed into the method which then gets all relevant information for that given User
    *@return the GUI for a given User to display all posts/comments/information
    */
    public Scene getScene(User user) {
      //adding dummy data to test out the design
      //should not be present in finalized version
        
        BorderPane root = new BorderPane();
        root.setPrefSize(SCENE_WIDTH,SCENE_HEIGHT);
        VBox userInfo = createUserInfo(user);
        //VBox postsPane = addPosts(user.getPosts());
        VBox commentsPane = addComments(user.getComments());
        
        root.setTop(userInfo);
        //root.setCenter(postsPane);
        root.setCenter(commentsPane);
   
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

   //Creates a VBox on the Top of the BorderPane with all info of the user
   //We can change the font/size/color of stuff if we want to later
    private VBox createUserInfo(User user) {
      VBox info = new VBox(15);

        Label username  = new Label("/u/" + user.getUsername());
        username.setFont(Font.font("Verdana", FontWeight.BOLD, 60));

        Button back = new Button();
        Image backImage = new Image(getClass().getResourceAsStream("images/back.png"));
        ImageView processedImage = new ImageView(backImage);
        back.setOnAction(evt -> controller.requestBack());
        processedImage.setFitHeight(75);
        processedImage.setFitWidth(75);
        back.setGraphic(processedImage);

        Node numPane = addNums(user);
        Node buttonPane = addButtons();
      
        info.setAlignment(Pos.CENTER);
      
        info.getChildren().addAll(username, numPane, buttonPane, back);
        return info;
    }
    
    //Adds the posts/comments buttons that switch the information shown below the topPane of BorderPane
    private Node addButtons(){
      GridPane button = new GridPane();
      button.setAlignment(Pos.CENTER);
      
      Button posts = new Button("Posts");
      button.add(posts,0,0);
      button.setHalignment(posts, HPos.RIGHT);
      
      Button comments = new Button("Comments");
      button.add(comments,1,0);
      
      //TODO when pressing buttons switch the centerPane of the border pane to
      //the corresponding button. Maybe reset the page?
      
      return button;
    }
    
    //Scraps all relevant information of a user to display it on their page.
    //User is given to the scene at the very begining
    private Node addNums(User user){
       GridPane nums = new GridPane();
       nums.setAlignment(Pos.CENTER);
      
       Label num1 = new Label(Integer.toString(user.getPostKarma()));
       nums.add(num1,1,0);
      
       Label info1 = new Label("Post Karma: ");
       nums.add(info1,0,0);
       nums.setHalignment(info1, HPos.RIGHT);

      
       Label info2 = new Label("Comment Karma: ");
       nums.add(info2,0,1);
       nums.setHalignment(info2, HPos.RIGHT);

       Label num2 = new Label(Integer.toString(user.getLinkKarma()));
       nums.add(num2,1,1);
      
       return nums;
      }

   //Creates a VBox of posts for this user and allows users to up/down vote the 
   //corresponding posts if they want
   private VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        for (int i = 0; i < POSTS_PER_PAGE; i ++){
            GridPane post = addPostPreview(i, postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }
    
    //Helper method to add all posts into the VBox CenterPane
    private GridPane addPostPreview(int postNumber, PostPreview postPreview) {
        GridPane postPane = new GridPane();
        postPane.setPadding(new Insets(0,10,0,10));
        postPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        postPane.setVgap(0);
        postPane.setHgap(5);

        Button upvote = new Button("+");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(upvote, 1, 0);

        Text postNumb = new Text(Integer.toString(postNumber));
        postPane.add(postNumb, 0, 1);

        Label voteCount = new Label(postPreview.getVote());
        postPane.add(voteCount, 1, 1);
        postPane.setHalignment(voteCount, HPos.CENTER);

        Hyperlink postContent = new Hyperlink(postPreview.getTitle());
        postContent.setMinWidth(MIN_TITLE_WIDTH/2);
        postContent.setOnAction(evt -> controller.requestContentPage(postPreview.getContentURL()));
        postPane.add(postContent, 2, 1);

        String userEntry = "By " + postPreview.getUsername();
        Text username = new Text(userEntry);
        postPane.add(username, 3, 1);

        Button downvote = new Button("-");
        downvote.setMinWidth(MIN_UPVOTE_WIDTH);
        postPane.add(downvote, 1, 2);

        Button comments = new Button("Comments");
        comments.setMinWidth(MIN_UPVOTE_WIDTH);
        /* TODO on click, the comments button should go to the comments page
         */
        postPane.add(comments, 3, 2);

        return postPane;
    }

   //Creates the comment VBox for this user that can then be up/down voted
   //Also, should we add the 'context' button? I guess thats the URL variable of commentList
   //but I'm not sure
    private VBox addComments(List<CommentPreview> commentList) {
        VBox commentVBox = new VBox();
        for (int i = 0; i < commentList.size(); i ++){
            GridPane comment = addCommentPreview(i, commentList.get(i));
            commentVBox.getChildren().add(comment);
        }
        return commentVBox;
    }
    
    //Helper method to create the scheme of the displayed comment
    //shows relevant info such as up/down cote, main content
    private GridPane addCommentPreview(int commentNumber, CommentPreview commentPreview) {
        GridPane commentPane = new GridPane();
        commentPane.setPadding(new Insets(10,10,10,10));
        commentPane.setMinSize(SCENE_WIDTH/2, SCENE_HEIGHT/10);
        commentPane.setVgap(0);
        commentPane.setHgap(5);

        Button upvote = new Button("+");
        upvote.setMinWidth(MIN_UPVOTE_WIDTH);
        commentPane.add(upvote, 1, 0);

        Text commentNumb = new Text(Integer.toString(commentNumber));
        commentPane.add(commentNumb, 0, 1);

        Label voteCount = new Label(Integer.toString(commentPreview.getVote()));
        commentPane.add(voteCount, 1, 1);
        commentPane.setHalignment(voteCount, HPos.CENTER);

        Hyperlink commentContent = new Hyperlink(commentPreview.getTitle());
        commentContent.setMinWidth(MIN_TITLE_WIDTH/2);
        commentContent.setOnAction(evt -> controller.requestPostPage(commentPreview.getURL()));
        commentPane.add(commentContent, 2, 1);

        Button downvote = new Button("-");
        downvote.setMinWidth(MIN_UPVOTE_WIDTH);
        commentPane.add(downvote, 1, 2);

        return commentPane;
    }

}
