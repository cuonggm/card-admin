package com.cuong.viewmodels;

import java.util.Date;

public class Word implements com.cuong.models.Cloneable<Word> {

	private String id;
	private String kanji;
	private String hiragana;
	private String meaning;
	private String amHanViet;
	private Date createdAt;
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKanji() {
		return kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public String getHiragana() {
		return hiragana;
	}

	public void setHiragana(String hiragana) {
		this.hiragana = hiragana;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getAmHanViet() {
		return amHanViet;
	}

	public void setAmHanViet(String amHanViet) {
		this.amHanViet = amHanViet;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void cloneTo(Word destination) {
		destination.setId(getId());
		destination.setKanji(getKanji());
		destination.setHiragana(getHiragana());
		destination.setMeaning(getMeaning());
		destination.setAmHanViet(getAmHanViet());
		destination.setCreatedAt(getCreatedAt());
		destination.setUpdatedAt(getUpdatedAt());
	}

}
