package com.cuong.daos.impl;

import java.util.HashMap;
import java.util.Map;

import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.models.Word;
import com.cuong.utils.C;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WordDAOImpl extends GenericDAO<String, Word> implements WordDAO {

	@Override
	public DatabaseReference getRootRef() {
		return FirebaseDatabase.getInstance().getReference().child(C.Ref.WORDS);
	}

	@Override
	public void updateKanji(String id, String kanji, OnComplete<Word> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.Word.KANJI, kanji);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (onComplete != null) {
					if (error == null) {
						getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								Word word = snapshot.getValue(Word.class);
								onComplete.onSuccess(word);
							}

							@Override
							public void onCancelled(DatabaseError error) {
								onComplete.onError(error.getMessage());
							}
						});
					} else {
						onComplete.onError(error.getMessage());
					}
				}
			}
		});
	}

	@Override
	public void updateHiragana(String id, String hiragana, OnComplete<Word> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.Word.HIRAGANA, hiragana);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (onComplete != null) {
					if (error == null) {
						getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								Word word = snapshot.getValue(Word.class);
								onComplete.onSuccess(word);
							}

							@Override
							public void onCancelled(DatabaseError error) {
								onComplete.onError(error.getMessage());
							}
						});
					} else {
						onComplete.onError(error.getMessage());
					}
				}
			}
		});
	}

	@Override
	public void updateMeaning(String id, String meaning, OnComplete<Word> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.Word.MEANING, meaning);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (onComplete != null) {
					if (error == null) {
						getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								Word word = snapshot.getValue(Word.class);
								onComplete.onSuccess(word);
							}

							@Override
							public void onCancelled(DatabaseError error) {
								onComplete.onError(error.getMessage());
							}
						});
					} else {
						onComplete.onError(error.getMessage());
					}
				}
			}
		});
	}

	@Override
	public void updateAmHanViet(String id, String amHanViet, OnComplete<Word> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.Word.AM_HAN_VIET, amHanViet);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (onComplete != null) {
					if (error == null) {
						getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								Word word = snapshot.getValue(Word.class);
								onComplete.onSuccess(word);
							}

							@Override
							public void onCancelled(DatabaseError error) {
								onComplete.onError(error.getMessage());
							}
						});
					} else {
						onComplete.onError(error.getMessage());
					}
				}
			}
		});
	}

}
