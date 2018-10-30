package services.xis.kadaejeon;

import java.util.ArrayList; 

class Article {
	private String title;
	private String content;
	private ArrayList comments;
	private int index;

	Article () {
		this.comments = new ArrayList<String>();
		this.index = 0;
	}

	Article(String title, String content, ArrayList comments, int index) {
		this.title = title;
		this.content = content;
		this.comments = comments;
		this.index = index;
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

	void setIndex (int index) {
		this.index = index;
	}

	int getIndex () {
		return this.index;
	}

}
