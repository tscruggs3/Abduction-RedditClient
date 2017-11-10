/**
 * @author Jordan Sybesma
 * 
 * A class for storing data about a subreddit
 */

import java.util.List;
import java.util.ArrayList;

public class Subreddit {

	private String title;
	private String sidebarContent;
	private List<PostPreview> postList;

	/**
	 * Constructor
	 * @param title The title of the subreddit
	 * @param sidebarContent The HTML sidebar content of the subreddit
	 */
	public Subreddit(String title, String sidebarContent) {
		this.title = title;
		this.sidebarContent = sidebarContent;
		postList = new ArrayList<PostPreview>();
	}

	/**
	 * Adds a post preview to the subreddit
	 * @param newPostPreview Post preview to be added
	 */
	public void addPostPreview(PostPreview newPostPreview) {
		postList.add(newPostPreview);
	}

	/**
	 * Returns the title of the subreddit
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the sidebar content of the subreddit
	 * @return HTML Sidebar Content as a String
	 */
	public String getSidebarContent() {
		return sidebarContent;
	}

	/**
	 * Returns the list of post previews associated with the subreddit
	 * @return List of post previews
	 */
	public List<PostPreview> getPostList() {
		return postList;
	}

}