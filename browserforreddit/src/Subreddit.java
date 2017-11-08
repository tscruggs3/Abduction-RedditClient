/*
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

	public Subreddit(String title, String sidebarContent) {
		this.title = title;
		this.sidebarContent = sidebarContent;
		postList = new ArrayList<PostPreview>();
	}

	public void addPostPreview(PostPreview newPostPreview) {
		postList.add(newPostPreview);
	}

	public String getTitle() {
		return title;
	}

	public String getSidebarContent() {
		return sidebarContent;
	}

	public List<PostPreview> getPostList() {
		return postList;
	}

}