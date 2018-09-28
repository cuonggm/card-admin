package com.cuong.modelconverters;

import java.util.Calendar;

import com.cuong.models.Word;

public class WordConverter {

	public static com.cuong.viewmodels.Word getViewModel(Word word) {
		com.cuong.viewmodels.Word viewModelWord = new com.cuong.viewmodels.Word();
		viewModelWord.setId(word.getId());
		viewModelWord.setKanji(word.getKanji());
		viewModelWord.setHiragana(word.getHiragana());
		viewModelWord.setMeaning(word.getMeaning());
		viewModelWord.setAmHanViet(word.getAmHanViet());
		Calendar createdAtCalendar = Calendar.getInstance();
		createdAtCalendar.setTimeInMillis(word.getCreatedAt());
		viewModelWord.setCreatedAt(createdAtCalendar.getTime());
		Calendar updatedAtCalendar = Calendar.getInstance();
		updatedAtCalendar.setTimeInMillis(word.getUpdatedAt());
		viewModelWord.setUpdatedAt(updatedAtCalendar.getTime());
		return viewModelWord;
	}

}
