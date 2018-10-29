package services.xis.kadaejeon;

import java.util.ArrayList; 

class Article {
	private String title;
	private String content;
	private ArrayList comments;

	Article () {
		this.comments = new ArrayList<String>();
	}

	Article(String title, String content, ArrayList comments) {
		this.title = title;
		this.content = content;
		this.comments = comments;
	}

	void setTitle (String title) {
		this.title = title;
	}

	String getTitle () {
		return this.title;
	}

	void setContent (String content) {
		this.content = content;
	}

	String getContent () {
		return this.content;
	}

	void setComments (ArrayList comments) {
		this.comments = comments;
	}

	ArrayList getComments () {
		return this.comments;
	}

}
