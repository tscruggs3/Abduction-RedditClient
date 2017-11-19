import java.util.List;
import java.util.ArrayList;

/**
 * A class for storing individual comment data in a tree structure
 * @author Jordan Sybesma
 */
public class Comment {

	private List<Comment> children;
	private Comment parent;
	private String username;
	private String content;
	private String votes;
	private Boolean rootStatus = false;

	/**
	 * Default constructor for null, root comment
	 */
	public Comment() {
		rootStatus = true;
		this.children = new ArrayList<Comment>();
	}

	/**
	 * Constructor
	 * @param parent The parent of the comment. Set to 'null' if no parent exists. &#39;
	 * @param username The username of the comment submitter
	 * @param content The content of the comment
	 * @param votes The current vote count of the comment
	 */
	public Comment(Comment parent, String username, String content, String votes) {
		this.parent = parent;
		this.username = username;
		this.content = SceneRender.filterText(content);
		this.votes = votes;
		this.children = new ArrayList<Comment>();
	}

	public Boolean isRoot() {
		return this.rootStatus;
	}

	/**
	 * Adds a child comment
	 * @param newChild Child comment
	 */
	public void addChild(Comment newChild) {
		children.add(newChild);
	}

	/**
	 * Returns the children of the coment
	 * @return List of children
	 */
	public List<Comment> getChildren() {
		return children;
	}

	/**
	 * Returns the current vote count of the post
	 * @return Vote count
	 */
	public String getVotes() {
		return votes;
	}

	/**
	 * Returns the username of the comment submitter
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the content of the comment
	 * @return Comment content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns the parent of the comment
	 * @return Comment content
	 */
	public Comment getParent() {
		return parent;
	}

}