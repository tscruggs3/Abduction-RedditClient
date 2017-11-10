/**
 * @author Jordan Sybesma
 *
 * A class for storing data about an individual reddit post link, as previewed from
 * the subreddit and user views
 */

public class PostPreview {

	private String contentURL; // url to the associated content
	private String commentURL; // url to the associated comment thread
	private String username;
	private String title;
	private int vote;

	/**
	 * Constructor
	 * @param contentURL The URL of the post content (either a comment thread or an external link.
	 * @param commentURL The URL of the associated comment thread
	 * @param username The username of the submitting user
	 * @param title The title of the post
	 * @param vote The current vote count of the post
	 */
	public PostPreview(String contentURL, String commentURL, String username, String title, int vote) {
		this.contentURL = contentURL;
		this.commentURL = commentURL;
		this.username = username;
		this.title = title;
		this.vote = vote;
	}

	/**
	 * Returns the content url
	 * @return Content URL
	 */
	public String getContentURL() {
		return contentURL;
	}

	/**
	 * Returns the comment thread url
	 * @return Comment thread URL
	 */
	public String getCommentURL() {
		return commentURL;
	}

	/**
	 * Returns the submitting username
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the title of the post
	 * @return Post title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the current vote count of the post
	 * @return votes
	 */
	public int getVote() {
		return vote;
	}
}