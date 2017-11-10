import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testLoader extends Application {

    @Override
    public void start(Stage primaryStage) {
        Post fakePost = buildFakePost();

        PostGUI renderer = new PostGUI();
        Scene scene = renderer.render(fakePost);

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Post buildFakePost() {
        Subreddit demoSub = new Subreddit("/r/test", "<p> plz work thx </p>");
        Post demoPost = new Post("/u/janedoe","test post plz ignore", "thanks", 42, demoSub);
        Comment initial = new Comment(null, "/u/spez","lmao this is lit",99);
        Comment secondTopLevel = new Comment(null, "/u/nobody","why did u even post this", -39);
        demoPost.addComment(initial);
        demoPost.addComment(secondTopLevel);
        initial.addChild(new Comment(initial,"/u/jordansybesma","no.",9001));
        initial.addChild(new Comment(initial,"/u/jordansybesma","yes.",9002));

        return demoPost;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
