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
	private List<Comment> comments;
	private int votes;
	private Subreddit subreddit;

	/**
	 * Constructor
	 * @param username User who posted
	 * @param title Title of the post
	 * @param content Content of the post
	 * @param votes Current vote count
	 * @param subreddit Associated subreddit
	 */
	public Post(String username, String title, String content, int votes, Subreddit subreddit) {
		this.username = username;
		this.title = title;
		this.content = content;
		this.votes = votes;
		comments = new ArrayList<Comment>();
		this.subreddit = subreddit;
	}

	/**
	 * Add a top level comment to the post
	 * @param newComment Comment to be added
	 */
	public void addComment(Comment newComment) {
		comments.add(newComment);
	}

	/**
	 * Returns the list of top level comments
	 * @return List of top level comments
	 */
	public List<Comment> getComments() {
		return comments;
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
	public int getVotes() {
		return votes;
	}

	/**
	 * Returns the associated subreddit object
	 * @return Subreddit
	 */
	public Subreddit getSubreddit() {
		return subreddit;
	}
}