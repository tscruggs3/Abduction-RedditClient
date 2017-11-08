/* 
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

	public User(String username, int postKarma, int linkKarma) {
		this.username = username;
		this.postKarma = postKarma;
		this.linkKarma = linkKarma;
		postList = new ArrayList<PostPreview>();
		commentList = new ArrayList<CommentPreview>();
	}

	public void addPost(PostPreview post) {
		postList.add(post);
	}

	public void addComment(CommentPreview comment) {
		commentList.add(comment);
	}

	public List<PostPreview> getPosts() {
		return postList;
	}

	public List<CommentPreview> getComments() {
		return commentList;
	}

	public String getUsername() {
		return username;
	}

	public int getPostKarma() {
		return postKarma;
	}

	public int getLinkKarma() {
		return linkKarma;
	}

}