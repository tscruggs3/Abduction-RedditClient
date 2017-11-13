/**
 * Created by mannesn on 11/9/17.
 */

import com.jaunt.*;

import java.util.ArrayList;
import java.util.List;

public class RedditScraper{

    public RedditScraper(){

    }
    /*
    title
    votes
    user
    comment
    content link
    subreddit
     */
    //Scrapes a url and returns a Subreddit object
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

    public Post post(String url){
        UserAgent user = new UserAgent();
        try{
            user.visit(url);
        }
        catch(JauntException e){
            System.err.println(e);
        }




        return null;
    }

    public static void main(String[]args){
            RedditScraper user = new RedditScraper();
            user.subReddit("http://www.reddit.com/r/random");
    }
}