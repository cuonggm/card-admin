package com.cuong.services.impl;

import com.cuong.daos.BaseDAO;
import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.models.Word;
import com.cuong.services.WordService;

public class WordServiceImpl extends GenericService<String, Word> implements WordService {

	private WordDAO wordDAO = new WordDAOImpl();

	@Override
	public void updateKanji(String id, String kanji, OnComplete<Word> onComplete) {
		wordDAO.updateKanji(id, kanji, onComplete);
	}

	@Override
	public void updateHiragana(String id, String hiragana, OnComplete<Word> onComplete) {
		wordDAO.updateHiragana(id, hiragana, onComplete);
	}

	@Override
	public void updateMeaning(String id, String meaning, OnComplete<Word> onComplete) {
		wordDAO.updateMeaning(id, meaning, onComplete);
	}

	@Override
	public void updateAmHanViet(String id, String amHanViet, OnComplete<Word> onComplete) {
		wordDAO.updateAmHanViet(id, amHanViet, onComplete);
	}

	@Override
	public BaseDAO<String, Word> getMainDAO() {
		return wordDAO;
	}

}
