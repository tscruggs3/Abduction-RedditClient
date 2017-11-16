/**
 * @author Jordan Sybesma
 *
 * A class for storing data about an individual reddit post
 */

import java.util.List;
import java.util.ArrayList;

public class Post {

	private String username;
	private String title;
	private String content;
	private Comment root;
	private String votes;
	private String subreddit;
	private String contentURL;

	/**
	 * Constructor
	 * @param username User who posted
	 * @param title Title of the post
	 * @param content Content of the post
	 * @param votes Current vote count
	 * @param subreddit Associated subreddit
	 */
	public Post(String username, String title, String content, String votes, String contentURL, String subreddit, Comment root) {
		this.username = username;
		this.title = title;
		this.content = content;
		this.votes = votes;
		this.root = root;
		this.subreddit = subreddit;
		this.contentURL = contentURL;
	}

	/**
	 * Returns the list of top level comments
	 * @return List of top level comments
	 */
	public Comment getRoot() {
		return root;
	}

	/**
	 * Returns the username of the user who posted this post
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the title of the post
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the content of the post
	 * @return Content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns the current vote count
	 * @return Votes
	 */
	public String getVotes() {
		return votes;
	}

	public String getContentURL() {
		return contentURL;
	}

	/**
	 * Returns the associated subreddit object
	 * @return Subreddit
	 */
	public String getSubreddit() {
		return subreddit;
	}
}