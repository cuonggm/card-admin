package com.cuong.viewmodels;

import java.util.Date;

public class List {

	private String id;
	private String name;
	private Long numberOfWords;
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumberOfWords() {
		return numberOfWords;
	}

	public void setNumberOfWords(Long numberOfWords) {
		this.numberOfWords = numberOfWords;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
