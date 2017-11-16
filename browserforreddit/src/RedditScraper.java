/**
 * Created by mannesn on 11/9/17.
 */

import com.jaunt.*;

import java.util.ArrayList;
import java.util.List;

public class RedditScraper {

    public static void main(String[]args){
        RedditScraper user = new RedditScraper();
        //user.subReddit("http://www.reddit.com/r/random");
        user.scrapePost("https://www.reddit.com/r/politics/comments/7cqez8/the_secret_correspondence_between_donald_trump_jr/");
    }

   public static User scrapeUser(String url){
        UserAgent user = new UserAgent();
        try{
            user.visit(url);
        }catch(JauntException e){
            return dummyUser();
        }
        Element userDocument = user.doc;
        String username = getUsername(url);
        String postKarma = getPostKarma(userDocument);
        String linkKarma = getLinkKarma(userDocument);
        User scrapedUser = new User(username, postKarma, linkKarma);
        getPostsAndComments(scrapedUser, userDocument);
        return scrapedUser;
   }
   private static void getPostsAndComments(User user, Element document){

       try{
           document = document.findFirst("div id=siteTable>");
       }catch(NotFound e){
           System.out.println("could not scrape user posts");
           return;
       }
       Elements history = document.findEach("<div data-author='" + user.getUsername() + "'>");
       for (Element post: history) {
           String dataAttribute;
           try{
               dataAttribute = post.getAt("data-type");
           } catch( NotFound e){
               dataAttribute = "nope";
           }
           if(dataAttribute == "link"){
               user.addPost(scrapeUserPost(post));
           }
           if(dataAttribute == "comment"){
               user.addComment(scrapeUserComment(post));
           }
       }
   }

   private static String getSubredditFromLink(String s){
       String containsAnswer = s.substring(s.lastIndexOf("/r/"));
       return containsAnswer.substring(0, containsAnswer.indexOf("/"));
   }


   private static PostPreview scrapeUserPost(Element post){
       try {
           String postLink = post.getAt("data-url");
           String username = post.getAt("data-author");
           String commentLink = "http://www.reddit.com/" + post.getAt("data-permalink");
           String upvotes = post.getAt("data-score");
           String numComments = post.getAt("data-comments-count");
           Element titleElement = post.findFirst("<p class='title'>");
           String title = titleElement.findFirst("<a>").getText();
           String timing = post.findFirst("<p class='tagline'>").findFirst("<time>").getText();
           return new PostPreview(postLink, commentLink, username, title,  upvotes, numComments);
       } catch (NotFound e){
           return new PostPreview("Error", "Error","Error","Error","Error","Error");
       }
   }

   private static CommentPreview scrapeUserComment(Element post){
       String title;//
       String postUrl;//
       String commentsUrl;//
       String subreddit;//
       String comment;//
       String votes;//
       String postTiming;//
       try{
           Element titlePost = post.findFirst("<a class='title'>");
           title = titlePost.getText();
           postUrl = titlePost.getAt("href");
           Element linkToComments = post.findFirst("<li class='first'>").findFirst("<a>");
           commentsUrl = "http://www.reddit.com" + linkToComments.getAt("href");
           subreddit = getSubredditFromLink(commentsUrl);
           Element tagline = post.findFirst("<p class='tagline'>");
           postTiming = tagline.findFirst("<time>").getText();
           votes = tagline.findFirst("<span class='score unvoted'>").getAt("title");
           comment = post.findFirst("<div class='md'>").getText();

           return new CommentPreview(title, postUrl, commentsUrl, subreddit, postTiming, comment, votes);

       }catch(NotFound e){
           System.err.println(e);
           return new CommentPreview("error", "error", "error", "error","error", "error","error");
       }
   }

/*
    public CommentPreview(String title, String originalPostUrl, String fullCommentsUrl, String subreddit, String postdate, String comment, String vote) {
        this.title = title;
        this.originalPostUrl = originalPostUrl;
        this.fullCommentsUrl = fullCommentsUrl;
        this.subreddit = subreddit;
        this.postdate = postdate;
        this.comment = comment;
        this.vote = vote;
    }
  */
   private static String getUsername(String link){
       return link.substring(link.lastIndexOf("user/") + 1);
   }

   private static String getPostKarma(Element userDocument){
       String returnValue;
       try{
           returnValue = userDocument.findFirst("<span class='karma'>").getText();
       }catch(NotFound e){
           returnValue = "Could not scrape Karma";
       }
       return returnValue;
   }

    private static String getLinkKarma(Element userDocument){
        String returnValue;
        try{
            returnValue = userDocument.findFirst("<span class='karma comment-karma'>").getText();
        }catch(NotFound e){
            returnValue = "Could not scrape Karma";
        }
        return returnValue;
    }

   private static User dummyUser(){
       return new User("Jane Doe", "-123456789", "-123456789");
   }


    public static Post scrapePost(String url){
        UserAgent user = new UserAgent();
        try{
            user.visit(url);
        }catch (JauntException e){
            System.err.println("ERROR: Could not open post URL!");
            return dummyPost();
        }
        Element postData = user.doc;
        try {
            postData = user.doc.findFirst("<div class='sitetable linklisting'").getElement(0);
        }catch(NotFound e){
            System.err.println("ERROR: Could not get postData!");
            return dummyPost();
        }
        //System.out.println(postData);
        try {
            String author = postData.getAt("data-author");
            String votes = postData.getAt("data-score");
            String postUrl = postData.getAt("data-permalink");
            String subreddit = postData.getAt("data-subreddit");
            String title = postData.findFirst("<div class='top-matter'>").findFirst("<a>").getText();
            String content;
            try{
                postData.findFirst("<div class='md'>");
                content = postData.getText();
            } catch(NotFound e){
                content = "";
            }
            // Determine if post has content.  If not, return ""
            return new Post(author, title, content, votes, url, subreddit, scrapeComments(url, user));
        }catch(NotFound e){
            System.err.println("ERROR: Could not scrape post content!!");
            return dummyPost();
        }

    }

    private static Post dummyPost(){
        return new Post("author", "title", "content", "1", "http://www.reddit.com", "/r/Test",new Comment());
    }

    private static Comment scrapeComments(String url, UserAgent user){
        Element commentSection;
        Comment nestedComments = new Comment();
        try {
            commentSection = user.doc.findFirst("<div class='sitetable nestedlisting'>");
            recursiveScrape(5, commentSection, nestedComments);
        }
        catch(NotFound e){
            System.err.println(e);
        }
        System.out.println("Comments scraped!");
        return nestedComments;
    }

    private static void recursiveScrape(int depth, Element commentSection, Comment parent){
        if(depth < 0){
            return;
        }
        else{
            Elements listOfComments = commentSection.findEach("<div data-type='comment'>");
            if(listOfComments == null){
                return ;
            }
            for(Element comment: listOfComments){
                Element next;
                try{
                    next = comment.findFirst("<div class='sitetable listing'>");
                } catch(NotFound e){
                    return;
                }
                String author = getAuthor(comment);
                String text = getText(comment);
                String votes = getVotes(comment);
                Comment temp = new Comment(parent, author, text, votes);
                //System.out.println(offset(depth) + author);

                parent.addChild(temp);
                recursiveScrape(depth - 1, next, temp);
            }
            return;
        }
    }

    private static String offset(int depth){
        String returnme = "";
        for(int i = 0; i < depth; i++){
            returnme += "        ";
        }
        return returnme;
    }

    private static String getVotes(Element comment){
        try {
            Element temp = comment.findFirst("<span class='score unvoted'>");
            return temp.getAt("title");
        }
        catch(NotFound e){
            return "0";
        }
    }

    private static String getAuthor(Element comment){
        try{
            return comment.getAt("data-author");
        } catch(NotFound e){
            return "Error: author not found";
        }
    }

    private static String getText(Element comment){
        Element text;
        try {
            text = comment.findFirst("<div class='md'>");
        } catch(NotFound e){
            return "error: no post text found";
        }
        return text.outerHTML();
    }

    public static Subreddit scrapeSubreddit(String url){
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
        Subreddit subreddit = new Subreddit(title);
        for(int i = 0; i < postLinks.size(); i++){
            PostPreview post = new PostPreview(postLinks.get(i), linksToComments.get(i), usernames.get(i),
                        titles.get(i), numUpvotes.get(i), numComments.get(i));
            subreddit.addPostPreview(post);
        }
        return subreddit;
    }

    private static List<String> getPostLinks(UserAgent user) {
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

    private static List<String> getNumComments(UserAgent user) {
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

    private static List<String> getLinksToComments(UserAgent user) {
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

    private static List<String> getNumUpvotes(UserAgent user) {
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

    private static List<String> getUsers(UserAgent user){
        Elements users = user.doc.findEvery("<p class='tagline '");
        List<Element> templist = users.toList();
        List<String> returnList = new ArrayList<String>();
        for(int i = 0; i < templist.size(); i++){
            try {
                Element item = templist.get(i).findFirst("<a>");
                String link = item.getText();
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

    private static List<String> getPostTitles(UserAgent user){
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
