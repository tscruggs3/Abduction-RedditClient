/* 
 * @author Jordan Sybesma
 *
 * A class for storing preview data about an individual comment, used for links on 
 * user pages.
 */

public class CommentPreview {

	private String title;
	private String URL;
	private int vote;

	public CommentPreview(String title, String URL, int vote) {
		this.title = title;
		this.URL = URL;
		this.vote = vote;
	}

	public String getTitle() {
		return this.title;
	}

	public String getURL() {
		return this.URL;
	}

	public int getVote() {
		return this.vote;
	}

}