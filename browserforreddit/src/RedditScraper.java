/**
 * Created by mannesn on 11/9/17.
 */

import com.jaunt.*;

import java.util.ArrayList;
import java.util.List;

public class RedditScraper{

    public RedditScraper(){

    }

    public static void main(String[]args){
        RedditScraper user = new RedditScraper();
        //user.subReddit("http://www.reddit.com/r/random");
        user.scrapePost("https://www.reddit.com/r/politics/comments/7cqez8/the_secret_correspondence_between_donald_trump_jr/");
    }
    public Post scrapePost(String url){
        UserAgent user = new UserAgent();
        try{
            user.visit(url);
        }catch (JauntException e){
            return dummyPost();
        }
        Element postData = user.doc;
        try {
            postData = user.doc.findFirst("<div class='sitetable linklisting'").getElement(0);
        }catch(NotFound e){
            return dummyPost();
        }
        System.out.println(postData);
        try {
            String author = postData.getAt("data-author");
            String votes = postData.getAt("data-score");
            String postUrl = postData.getAt("data-permalink");
            String title = postData.findFirst("<div class='title'>").findFirst("<a>").getText();
            String content;
            try{
                postData.findFirst("<div class='md'>");
                content = postData.getText();
            } catch(NotFound e){
                content = "";
            }
            // Determine if post has content.  If not, return ""
            return new Post(author, title, content, scrapeComments(url, user), votes);
        }catch(NotFound e){
            return dummyPost();
        }

    }

    private Post dummyPost(){
        return new Post("author", "title", "content", "scrapeComments(url, user)", "votes");
    }

    private Comment scrapeComments(String url, UserAgent user){
        Element commentSection;
        Comment nestedComments = new Comment();
        try {
            commentSection = user.doc.findFirst("<div class='sitetable nestedlisting'>");
            recursiveScrape(3, commentSection, nestedComments);
        }
        catch(NotFound e){
            System.err.println(e);
            commentSection = user.doc;
        }
        return nestedComments;
    }

    private void recursiveScrape(int depth, Element commentSection, Comment parent){
        if(depth < 0){
            return;
        }
        else{
            //System.out.println(commentSection);
            Elements listOfComments = commentSection.findEach("<div data-type='comment'>");
            if(listOfComments == null){
                return ;
            }
            for(Element comment: listOfComments){
                Element next;
                try{
                    next = comment.findFirst("<div class='sitetable listing'>");
                    //System.out.println(next);
                } catch(NotFound e){
                    System.out.println("additional children comments not found");
                    return;
                }
                String author = getAuthor(comment);
                String text = getText(comment);
                int votes = getVotes(comment);
                Comment temp = new Comment(parent, author, text, votes);
                //
                System.out.println(offset(depth) + author);
                //System.out.println(offset(depth) + text);


                //
                parent.addChild(temp);
                recursiveScrape(depth - 1, next, temp);
            }
            return;
        }
    }

    private String offset(int depth){
        String returnme = "";
        for(int i = 0; i < depth; i++){
            returnme += "        ";
        }
        return returnme;
    }
    private int getVotes(Element comment){
        try {
            Element temp = comment.findFirst("<span class='score unvoted'>");
            return Integer.parseInt(temp.getAt("title"));
        }
        catch(NotFound e){
            return -500000;
        }
    }

    private String getAuthor(Element comment){
        try{
            return comment.getAt("data-author");
        } catch(NotFound e){
            return "Error: author not found";
        }
    }

    private String getText(Element comment){
        Element text;
        try {
            text = comment.findFirst("<div class='md'>");
        } catch(NotFound e){
            return "error: no post text found";
        }
        return text.outerHTML();
    }

    public Subreddit subReddit(String url){
        UserAgent user = new UserAgent();
        try {
            user.visit(url);
        } catch (JauntException e){
            System.err.println(e);
            return null;
        }
        String title;
        try {
            title = user.doc.findFirst("<title>").getText();
        } catch (NotFound e){
            title = "reddit";
        }

        List<String> titles = getPostTitles(user);
        List<String> postLinks = getPostLinks(user);
        List<String> usernames = getUsers(user);
        List<String> numUpvotes = getNumUpvotes(user);
        List<String> linksToComments = getLinksToComments(user);
        List<String> numComments = getNumComments(user);
        System.out.println(titles);
        System.out.println(postLinks);
        System.out.println(usernames);
        System.out.println(numUpvotes);
        System.out.println(linksToComments);
        System.out.println(numComments);
        Subreddit subreddit = new Subreddit(title, "");
        for(int i = 0; i < postLinks.size(); i++){
            PostPreview post = new PostPreview(postLinks.get(i), linksToComments.get(i), usernames.get(i),
                        titles.get(i), numUpvotes.get(i), numComments.get(i));
            subreddit.addPostPreview(post);
        }
        return subreddit;
    }

    private List<String> getPostLinks(UserAgent user) {
       Elements titles = user.doc.findEvery("<p class=title>");//title and link to content
       List<Element> templist = titles.toList();
       List<String> returnList = new ArrayList<String>();
       for(int i = 0; i < templist.size(); i++){
           try {
              Element item = templist.get(i);
              returnList.add(item.findFirst("<a>").getAt("href"));
           } catch (NotFound e){
               returnList.add("Scraper Error: link not found");
           }
       }
       return returnList;
    }

    private List<String> getNumComments(UserAgent user) {
        Elements linksToComments = user.doc.findEvery("<li class=first");//getElement(0);
        List<Element> templist = linksToComments.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++){
            try {
                Element item = templist.get(i).getElement(0);
                returnList.add(item.innerHTML());
            }
            catch (NotFound e){
                returnList.add("Scrape Error: number of comments not found");
            }
        }
        return returnList;
    }

    private List<String> getLinksToComments(UserAgent user) {
        Elements linksToComments = user.doc.findEvery("<li class=first");//getElement(0);
        List<Element> templist = linksToComments.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++){
            try {
                Element item = templist.get(i).getElement(0);
                returnList.add(item.getAtString("href"));
            }
            catch (NotFound e){
                returnList.add("Scrape Error: Comment link not found");
            }
        }
        return returnList;
    }

    private List<String> getNumUpvotes(UserAgent user) {
        Elements upvotes = user.doc.findEvery("<div class='score unvoted'>");
        List<Element> templist = upvotes.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++) {
            Element item = templist.get(i);
            if (item.getText() == "&bull;"){
                returnList.add("0");
            }
            else{
                returnList.add(item.getText());
            }
        }
        return returnList;
    }

    private List<String> getUsers(UserAgent user){
        Elements users = user.doc.findEvery("<p class='tagline '");
        List<Element> templist = users.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++){
            try {
                Element item = templist.get(i).findFirst("<a>");
                String link = item.getAt("href");
                //Test for whether or not the user has been deleted
                if (link.contains("/r/")){
                    link = "Deleted";
                }
                returnList.add(link);
            } catch (NotFound e){
                returnList.add("Scrape Error: User not found");
            }
        }
        return returnList;
    }

    private List<String> getPostTitles(UserAgent user){
        Elements titles = user.doc.findEvery("<p class=title>");//title and link to content
        List<Element> templist = titles.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++){
            try {
                Element item = templist.get(i).getElement(0);
                returnList.add(item.getText());
            } catch (NotFound e){
                returnList.add("Scraper Error: Title not found");
            }
        }
        return returnList;
    }




}
