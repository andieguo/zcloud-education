package com.education.experiment.commons;

public class NoteBean {

	private String user;
	private String title;
	private String content;
	private String dateContent;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDateContent() {
		return dateContent;
	}
	public void setDateContent(String dateContent) {
		this.dateContent = dateContent;
	}
	public NoteBean(String user, String title, String content,String dateContent) {
		super();
		this.user = user;
		this.title = title;
		this.content = content;
		this.dateContent = dateContent;
	}
	public NoteBean() {
		super();
	}
	@Override
	public String toString() {
		return "NoteBean [user=" + user + ", title=" + title + ", content=" + content + ", dateContent=" + dateContent
				+ "]";
	}
	
}
