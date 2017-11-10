import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testLoader extends Application {

    @Override
    public void start(Stage primaryStage) {
        Post fakePost = buildFakePost();

        PostGUI renderer = new PostGUI();
        Scene scene = renderer.getScene(fakePost);

        //UserGUI renderer = new UserGUI();
       // Scene scene = renderer.getScene(buildFakeUser());

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private User buildFakeUser() {
        User something =  new User("Name",122,33333);
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
