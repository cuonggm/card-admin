package com.cuong.models;

import java.util.ArrayList;

public class List implements BaseManageable<List> {

	private String id;
	private String name;
	private java.util.List<String> wordIDs = new ArrayList<>();
	private Long createdAt;
	private Long updatedAt;

	@Override
	public String getId() {
		return id;
	}

	@Override
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

	@Override
	public Long getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public Long getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void cloneTo(List destination) {
		destination.setId(getId());
		destination.setName(getName());
		destination.setWordIDs(getWordIDs());
		destination.setCreatedAt(getCreatedAt());
		destination.setUpdatedAt(getUpdatedAt());
	}

}
