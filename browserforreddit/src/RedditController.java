import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayDeque;
import java.util.Deque;

public class RedditController extends Application {

    private Deque<Scene> pages;
    private Stage mainStage;
    private SubredditGUI subredditRender;
    private PostGUI postRender;
    private UserGUI userRender;

    /**
     * Initializes our reddit application. This is the javafx
     * standard for how to open an application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        subredditRender = new SubredditGUI(this);
        postRender = new PostGUI(this);
        userRender = new UserGUI(this);
        mainStage = primaryStage;
        pages = new ArrayDeque<Scene>();
        pages.push(getInitialScene());
        primaryStage.setScene(pages.peek());
        primaryStage.show();
    }

    /**
     * This function is called by the GUIs in order to go to the previously visited page
     */
    public void requestBack() {
        backButton();
    }

    /**Scrapes a page and returns an appropriate gui
     *
     * @param url the url of the reddit page to scrape
     */
    public void requestPage(String url) {
        //TODO: change url to actually be urls instead of specific hardcoded references
        // NOTE: This will parse the HTML at "url" and return the appropriate objects.
        // But, for now, this is just looking up our demo objects and returning those instead.

        if (url.equals("post1")) {
            display(postRender.getScene(buildFakePost()));
        } else if (url.equals("/u/janedoe")) {
            display(userRender.getScene(buildFakeUser()));
        }
    }

    private Scene getInitialScene() {
        Subreddit dummy = new Subreddit("r/test", "<p> demo sidebar content </p>");
        PostPreview post1 = new PostPreview("post1", "post1","/u/janedoe","test post plz ignore",99191);
        PostPreview post2 = new PostPreview("post1", "post1","/u/janedoe","give me karma",-32000);
        dummy.addPostPreview(post1);
        dummy.addPostPreview(post2);

        return subredditRender.getScene(dummy);
    }

    private User buildFakeUser() {
        User something =  new User("u/janedoe",122,33333);
        PostPreview first = new PostPreview("content","comment", something.getUsername(),"Post1", 1222);
        PostPreview second = new PostPreview("content2","comment2", something.getUsername(),"Post2", 1222);
        CommentPreview firstC = new CommentPreview("content","Comment1", 1222);
        CommentPreview secondC = new CommentPreview("content2","Comment2", 1222);
        something.addPost(first);
        something.addPost(second);
        something.addComment(firstC);
        something.addComment(secondC);
        return something;
    }

    private Post buildFakePost() {
        Subreddit dummy = new Subreddit("r/test", "<p> demo sidebar content </p>");
        Post demoPost = new Post("/u/janedoe","test post plz ignore", "thanks", 42, dummy);
        Comment initial = new Comment(null, "/u/spez","lmao this is lit",99);
        Comment secondTopLevel = new Comment(null, "/u/nobody","why did u even post this", -39);
        demoPost.addComment(initial);
        demoPost.addComment(secondTopLevel);
        initial.addChild(new Comment(initial,"/u/jordansybesma","no.",9001));
        initial.addChild(new Comment(initial,"/u/jordansybesma","yes.",9002));

        return demoPost;
    }

    private void display(Scene scene){
        pages.push(scene);
        mainStage.setScene(pages.peek());;
    }

    private void backButton() {
        if(pages.size() > 1) {
            pages.pop();
        }
        mainStage.setScene(pages.peek());;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
