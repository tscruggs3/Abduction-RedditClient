/* 
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

	public PostPreview(String contentURL, String commentURL, String username, String title, int vote) {
		this.contentURL = contentURL;
		this.commentURL = commentURL;
		this.username = username;
		this.title = title;
		this.vote = vote;
	}

	public String getContentURL() {
		return contentURL;
	}

	public String getCommentURL() {
		return commentURL;
	}

	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}

	public int getVote() {
		return vote;
	}
}