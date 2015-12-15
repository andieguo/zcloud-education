package com.education.experiment.cloudlibrary;

public class Book {
	private String name;
	private String author;
	private String publishDate;
	private String section;

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return section;
	}
}
