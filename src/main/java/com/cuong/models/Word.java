package com.cuong.models;

public class Word implements BaseManageable<Word> {

	private String id;
	private String kanji;
	private String hiragara;
	private String meaning;
	private String amHanViet;
	private Long createdAt;
	private Long updatedAt;

	public Word() {

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
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
	public void cloneTo(Word destination) {
		destination.setId(getId());
		destination.setKanji(getKanji());
		destination.setHiragara(getHiragara());
		destination.setMeaning(getMeaning());
		destination.setAmHanViet(getAmHanViet());
		destination.setCreatedAt(getCreatedAt());
		destination.setUpdatedAt(getUpdatedAt());
	}

	@Override
	public String toString() {
		return getKanji() + "\n" + getHiragara() + "\n" + getMeaning() + "\n" + getAmHanViet();
	}

}
