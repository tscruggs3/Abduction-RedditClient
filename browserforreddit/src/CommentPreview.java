/**
 * @author Jordan Sybesma
 *
 * A class for storing preview data about an individual comment, used for links on 
 * user pages.
 */

public class CommentPreview {

	private String title;
	private String URL;
	private int vote;

	/**
	 * Constructor
	 * @param title String title of the object
	 * @param URL String URL of the associated comment
	 * @param vote Current vote count of the comment
	 */
	public CommentPreview(String title, String URL, int vote) {
		this.title = title;
		this.URL = URL;
		this.vote = vote;
	}

	/**
	 * Returns the title of comment
	 * @return Title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the URL of the comment
	 * @return URL
	 */
	public String getURL() {
		return this.URL;
	}

	/**
	 * Returns the current vote count of the comment
	 * @return Vote count
	 */
	public int getVote() {
		return this.vote;
	}

}