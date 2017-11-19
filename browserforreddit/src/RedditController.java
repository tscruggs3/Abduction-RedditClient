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
        requestSubredditPage("http://www.reddit.com/r/whowouldwin"); // Initial Page
        primaryStage.show();
    }

    /**
     * Switches from the current scene to the previous scene
     */
    public static void requestBack() {
        backButton();
    }

    /**
     * Scrapes a subreddit page and returns an appropriate gui
     * @param url the url of the reddit page to scrape
     */
    public static void requestSubredditPage(String url) {
        System.out.println("Requested Subreddit at: " + url);
        Subreddit scrapedContent = RedditScraper.scrapeSubreddit(url);
        if (scrapedContent == null) {
            display(SubredditGUI.getScene(new Subreddit("Subreddit Not Found","http://www.reddit.com/r/random")));
        } else {
            display(SubredditGUI.getScene(scrapedContent));
        }
    }

    /**
     * Scrapes a user page and returns an appropriate gui
     * @param url the URL of the user page to scrape
     * @param type of user page, "Comments" or "Posts" denotes which type of content to load first
     */
    public static void requestUserPage(String url, String type) {
        System.out.println("Requested User at: " + url);
        display(UserGUI.getScene(RedditScraper.scrapeUser(url), type));
    }

    /**
     * Scrapes a post page and returns an appropriate gui
     * @param url the URL of the post page to scrape
     */
    public static void requestPostPage(String url) {
        System.out.println("Requested Post at: " + url);
        Post postData = RedditScraper.scrapePost(url);
        System.out.println("Got post data, rendering!");
        display(PostGUI.getScene(postData));
    }

    /**
     * Returns an HTML view of any website, used to show non-reddit linked content
     * @param url the url of linked content
     * @param title the title of the post
     */
    public static void requestContentPage(String url, String title) {
        System.out.println("Requested Content at :" + url);
        if (url.contains("reddit")) {
            System.out.println("no content 4 u");
            if (url.contains("comments")) {
                requestPostPage(url);
            }
        } else if (url.contains("/r/")) {
            String newUrl = "http://www.reddit.com" + url;
            requestPostPage(newUrl);
        } else {
            display(ContentGUI.getScene(url, title));
        }
    }

    /**
     * Returns the subreddit search page
     */
    public static void requestSearchPage(){
        display(SearchPageGUI.getScene());
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

    /**
     * Executes the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}