package com.cuong.models;

public class Word implements TimeManageable {

	private String id;

	private String kanji;

	private String hiragara;

	private String meaning;

	private String amHanViet;

	private Long createdAt;

	public Word() {

	}

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

	public String getHiragara() {
		return hiragara;
	}

	public void setHiragara(String hiragara) {
		this.hiragara = hiragara;
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

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return getKanji() + "\n" + getHiragara() + "\n" + getMeaning() + "\n" + getAmHanViet();
	}

}
