package com.cuong.listeners;

import com.cuong.viewmodels.Word;

public interface WordTableViewEventListener {

	void onKanjiEditCommit(Word word, String oldValue, String newValue);

	void onHiraganaEditCommit(Word word, String oldValue, String newValue);

	void onMeaningEditCommit(Word word, String oldValue, String newValue);

	void onAmHanVietEditCommit(Word word, String oldValue, String newValue);

	void onDeleteButtonClicked(Word word);

}
