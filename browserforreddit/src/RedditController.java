import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayDeque;
import java.util.Deque;

public class RedditController extends Application {

    private static Deque<Scene> pages;
    private static Stage mainStage;

    /**
     * Initializes our reddit application. This is the javafx
     * standard for how to open an application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        pages = new ArrayDeque<Scene>();
        requestSubredditPage("http://www.reddit.com/r/highqualitygifs",0); // Initial Page
        primaryStage.show();
    }

    /**
     * This function is called by the GUIs in order to go to the previously visited page
     */
    public static void requestBack() {
        backButton();
    }

    /**Scrapes a page and returns an appropriate gui
     *
     * @param url the url of the reddit page to scrape
     */

    public static void requestSubredditPage(String url, int position) {
        System.out.println("Requested Subreddit at: " + url);
        display(SubredditGUI.getScene(RedditScraper.scrapeSubreddit(url + "/?count=" + position), position));
    }

    public static void requestUserPage(String url, String type) {
        System.out.println("Requested User at: " + url);
        display(UserGUI.getScene(RedditScraper.scrapeUser(url), type));
    }

    public static void requestPostPage(String url) {
        System.out.println("Requested Post at: " + url);
        Post postData = RedditScraper.scrapePost(url);
        System.out.println("Got post data, rendering!");
        display(PostGUI.getScene(postData));
    }

    public static void requestContentPage(String url, String title) {
        System.out.println("Requested Content at :" + url);
        if (url.contains("reddit")) {
            System.out.println("no content 4 u");
            if (url.contains("comments")) {
                requestPostPage(url);
            }
        } else {
            display(ContentGUI.getScene(url, title));
        }
    }

    private static Scene getInitialScene() {
        Subreddit dummy = new Subreddit("r/test");
        PostPreview post1 = new PostPreview("post1", "post1","janedoe","test post plz ignore","99191","32");
        PostPreview post2 = new PostPreview("post1", "post1","janedoe","give me karma","-32000","32");
        dummy.addPostPreview(post1);
        dummy.addPostPreview(post2);

        return SubredditGUI.getScene(dummy,0);
    }

    private static User buildFakeUser() {
        User something =  new User("janedoe","122","33333");
        return something;
    }

    private static Post buildFakePost() {
        Subreddit dummy = new Subreddit("r/test");
        Post demoPost = new Post("/u/janedoe","test post plz ignore", "thanks", "42", "", "test", new Comment());
        Comment initial = new Comment(null, "/u/spez","lmao this is lit","99");
        Comment secondTopLevel = new Comment(null, "/u/nobody","why did u even post this", "-39");
        demoPost.getRoot().addChild(initial);
        demoPost.getRoot().addChild(secondTopLevel);
        initial.addChild(new Comment(initial,"/u/jordansybesma","no.","9001"));
        initial.addChild(new Comment(initial,"/u/jordansybesma","yes.","9002"));

        return demoPost;
    }

    private static void display(Scene scene){
        pages.push(scene);
        mainStage.setScene(pages.peek());;
    }

    private static void backButton() {
        if(pages.size() > 1) {
            pages.pop();
        }
        mainStage.setScene(pages.peek());;
    }

    public static void main(String[] args) {
        launch(args);
    }
}