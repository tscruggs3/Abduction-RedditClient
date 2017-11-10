/**
 * @author Jordan Sybesma
 *
 * A class for storing data about an individual reddit user
 */

import java.util.List;
import java.util.ArrayList;

public class User {

	private String username;
	private List<PostPreview> postList;
	private List<CommentPreview> commentList;
	private int postKarma;
	private int linkKarma;

	/**
	 * Constructor
	 * @param username Username
	 * @param postKarma User's Post Karma
	 * @param linkKarma User's Link Karma
	 */
	public User(String username, int postKarma, int linkKarma) {
		this.username = username;
		this.postKarma = postKarma;
		this.linkKarma = linkKarma;
		postList = new ArrayList<PostPreview>();
		commentList = new ArrayList<CommentPreview>();
	}

	/**
	 * Adds a post to the User's history
	 * @param post Post to be added
	 */
	public void addPost(PostPreview post) {
		postList.add(post);
	}

	/**
	 * Adds a comment to the User's history
	 * @param comment Comment to be added
	 */
	public void addComment(CommentPreview comment) {
		commentList.add(comment);
	}

	/**
	 * Returns the posts associated with this user
	 * @return List of PostPreview
	 */
	public List<PostPreview> getPosts() {
		return postList;
	}

	/**
	 * Returns the list of comments associated with this user
	 * @return List of CommentPreview
	 */
	public List<CommentPreview> getComments() {
		return commentList;
	}

	/**
	 * Returns the username
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the post karma of the user
	 * @return Post Karma
	 */
	public int getPostKarma() {
		return postKarma;
	}

	/**
	 * Returns the link karma of the user
	 * @return Link Karma
	 */
	public int getLinkKarma() {
		return linkKarma;
	}

}