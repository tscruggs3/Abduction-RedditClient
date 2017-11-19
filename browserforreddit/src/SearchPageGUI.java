import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A class that creates the GUI for the search page view
 */

public class SearchPageGUI implements SceneRender {

    private RedditController controller;

    /**
     * Creates a scene object.
     *
     * @return a scene given the subreddit.
     */
    public static Scene getScene() {

        Text copyrightInfo = new Text("Reddit is a registered trademark of Reddit Inc.");

        HBox searchContent = createSearchContent();
        searchContent.setPadding(new Insets(20));

        HBox header = createHeader();
        //Sets the spacing between the title and the search options
        header.setMinHeight(100);

        Scene scene = new Scene(new BorderPane(searchContent, header, null, copyrightInfo, null), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    private static HBox createHeader() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setMinWidth(SCENE_WIDTH);

        Button back = SceneRender.buildBackButton();
        HBox searchTitle = SceneRender.addTitle("Find a Subreddit");

        //Sets the spacing between the back button and the title
        pane.setSpacing(255);
        pane.getChildren().addAll(back, searchTitle);

        return pane;
    }

    private static HBox createSearchContent() {
        HBox searchContent = new HBox();
        VBox searchBox = createSearchBox();
        VBox suggestedLinks = createSuggestedLinks();

        //Sets the spacing between the Search Bar and the Suggested links column
        searchContent.setSpacing(255);

        searchContent.getChildren().addAll(searchBox, suggestedLinks);

        return searchContent;
    }

    private static VBox createSearchBox(){
        VBox searchBox = new VBox();
        HBox title = SceneRender.addTitle("Search");
        TextField myTextfield = new TextField();
        myTextfield.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/"+ myTextfield.getText()));

        Button searchButton = new Button("Search");
        searchButton.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/"+ myTextfield.getText()));

        HBox searchBar = new HBox();
        searchBar.getChildren().addAll(myTextfield, searchButton);

        searchBox.getChildren().addAll(title, searchBar);

        return searchBox;
    }

    private static VBox createSuggestedLinks(){
        VBox suggestedLinksBox = new VBox();
        HBox title = SceneRender.addTitle("Suggested Subreddits");
        suggestedLinksBox.getChildren().add(title);

        List<String> suggestedList = new ArrayList<String>();
        suggestedList.add("showerthoughts");
        suggestedList.add("askreddit");
        suggestedList.add("pics");
        suggestedList.add("minneapolis");
        suggestedList.add("catsstandingup");
        suggestedList.add("wholesomememes");
        suggestedList.add("highqualitygifs");
        suggestedList.add("programmerhumor");
        suggestedList.add("carletoncollege");

        for (int i = 0; i < suggestedList.size(); i++){
            Hyperlink suggestionLink = makeLink(suggestedList.get(i));
            suggestedLinksBox.getChildren().add(suggestionLink);
        }

        return suggestedLinksBox;
    }

    private static Hyperlink makeLink(String linkName){
        Hyperlink myLink = new Hyperlink(linkName);
        myLink.setMinWidth(MIN_TITLE_WIDTH);
        myLink.setFont(Font.font(FONT_TYPE_CONTENT, FontWeight.BOLD, POST_TITLE_SIZE));
        myLink.setTextFill(POST_COLOR);
        myLink.setOnAction(evt -> RedditController.requestSubredditPage("http://www.reddit.com/r/"+ linkName));

        return myLink;
    }
}