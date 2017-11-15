import javafx.application.Application;
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
        requestSubredditPage("http://www.reddit.com/r/showerthoughts"); // Initial Page
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

    public void requestSubredditPage(String url) {
        System.out.println("Requested Subreddit at: " + url);
        display(subredditRender.getScene(RedditScraper.scrapeSubreddit(url)));
    }

    public void requestUserPage(String url) {
        System.out.println("Requested User at: " + url);
        display(userRender.getScene(buildFakeUser()));
    }

    public void requestPostPage(String url) {
        System.out.println("Requested Post at: " + url);
        Post postData = RedditScraper.scrapePost(url);
        System.out.println("Got post data, rendering!");
        display(postRender.getScene(postData));
    }

    public void requestContentPage(String url) {
        // TODO: Implement this.
    }

    private Scene getInitialScene() {
        Subreddit dummy = new Subreddit("r/test");
        PostPreview post1 = new PostPreview("post1", "post1","janedoe","test post plz ignore","99191","32");
        PostPreview post2 = new PostPreview("post1", "post1","janedoe","give me karma","-32000","32");
        dummy.addPostPreview(post1);
        dummy.addPostPreview(post2);

        return subredditRender.getScene(dummy);
    }

    private User buildFakeUser() {
        User something =  new User("janedoe",122,33333);
        return something;
    }

    private Post buildFakePost() {
        Subreddit dummy = new Subreddit("r/test");
        Post demoPost = new Post("/u/janedoe","test post plz ignore", "thanks", "42", "", dummy, new Comment());
        Comment initial = new Comment(null, "/u/spez","lmao this is lit","99");
        Comment secondTopLevel = new Comment(null, "/u/nobody","why did u even post this", "-39");
        demoPost.getRoot().addChild(initial);
        demoPost.getRoot().addChild(secondTopLevel);
        initial.addChild(new Comment(initial,"/u/jordansybesma","no.","9001"));
        initial.addChild(new Comment(initial,"/u/jordansybesma","yes.","9002"));

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