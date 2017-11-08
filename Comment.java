/*
 * @author Jordan Sybesma
 *
 * A class for storing individual comment data in a tree structure
 */

import java.util.List;
import java.util.ArrayList;

public class Comment {

	private List<Comment> children;
	private Comment parent;
	private String username;
	private String content;
	private int votes;

	public Comment(Comment parent, String username, String content, int votes) {
		this.parent = parent;
		this.username = username;
		this.content = content;
		this.votes = votes;
		this.children = new ArrayList<Comment>();
	}

	public void addChild(Comment newChild) {
		children.add(newChild);
	}

	public List<Comment> getChildren() {
		return children;
	}

	public int getVotes() {
		return votes;
	}

	public String getUsername() {
		return username;
	}

	public String getContent() {
		return content;
	}

	public Comment getParent() {
		return parent;
	}

}