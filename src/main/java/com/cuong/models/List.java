package com.cuong.models;

import java.util.ArrayList;

public class List implements TimeManageable {

	private String id;
	private String name;
	private java.util.List<String> wordIDs = new ArrayList<>();
	private Long createdAt;

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

	public java.util.List<String> getWordIDs() {
		return wordIDs;
	}

	public void setWordIDs(java.util.List<String> wordIDs) {
		this.wordIDs = wordIDs;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

}
