/* 
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

	public Post(String username, String title, String content, Comment root, int votes) {
		this.username = username;
		this.title = title;
		this.content = content;
		this.votes = votes;
		comments = new ArrayList<Comment>();
	}

	// Add a top level comment
	public void addComment(Comment newComment) {
		comments.add(newComment);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getVotes() {
		return votes;
	}

}