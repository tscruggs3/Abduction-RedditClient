/**
 * @author Jordan Sybesma
 *
 * A class for storing preview data about an individual comment, used for links on 
 * user pages.
 */

public class CommentPreview {

	/**
	 * Constructor
	 * @param title String title of the object
	 * @param originalPostUrl URL of the original post link
	 * @param fullCommentsUrl URL of the original comment thread
	 * @param subreddit the subreddit the comment was posted to
	 * @param postdate How recently the comment was posted
	 * @param comment The content of the comment
	 * @param vote The upvote/downvote count of the comment
	 */

	public CommentPreview(String title, String originalPostUrl, String fullCommentsUrl, String subreddit, String postdate, String comment, String vote) {
		this.title = SceneRender.filterText(title);
		this.originalPostUrl = originalPostUrl;
		this.fullCommentsUrl = fullCommentsUrl;
		this.subreddit = subreddit;
		this.postdate = postdate;
		this.comment = SceneRender.filterText(comment);
		this.vote = vote;
	}



	private String title;
	private String originalPostUrl;
	private String fullCommentsUrl;
	private String subreddit;
	private String postdate;
	private String comment;
	private String vote;





	public String getTitle() {
		return title;
	}

	public String getOriginalPostUrl() {
		return originalPostUrl;
	}

	public String getFullCommentsUrl() {
		return fullCommentsUrl;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public String getPostdate() {
		return postdate;
	}

	public String getComment() {
		return comment;
	}

	public String getVote() {
		return vote;
	}


}