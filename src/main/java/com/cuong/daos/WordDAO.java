package com.cuong.daos;

import com.cuong.models.Word;

public interface WordDAO extends BaseDAO<String, Word> {

	void updateKanji(String id, String kanji, OnComplete<Word> onComplete);

	void updateHiragana(String id, String hiragana, OnComplete<Word> onComplete);

	void updateMeaning(String id, String meaning, OnComplete<Word> onComplete);

	void updateAmHanViet(String id, String amHanViet, OnComplete<Word> onComplete);

}
