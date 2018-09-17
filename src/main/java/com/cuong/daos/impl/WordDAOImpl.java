package com.cuong.daos.impl;

import java.util.Date;

import com.cuong.daos.WordDAO;
import com.cuong.models.Word;
import com.cuong.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WordDAOImpl implements WordDAO {

	private DatabaseReference wordsRef = FirebaseDatabase.getInstance().getReference().child(Constant.REF_WORDS);

	@Override
	public Word save(Word word) {
		DatabaseReference wordRef = wordsRef.push();
		word.setId(wordRef.getKey());
		word.setCreatedAt(Long.valueOf(new Date().getTime()));
		wordRef.setValueAsync(word);
		return word;
	}

	@Override
	public boolean delete(String wordId) {
		wordsRef.child(wordId).removeValueAsync();
		return true;
	}

}
