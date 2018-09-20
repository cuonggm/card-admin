package com.cuong.daos.impl;

import com.cuong.daos.WordDAO;
import com.cuong.models.Word;
import com.cuong.utils.C;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WordDAOImpl extends GenericDAO<String, Word> implements WordDAO {

	@Override
	public DatabaseReference getRootRef() {
		return FirebaseDatabase.getInstance().getReference().child(C.Ref.WORDS);
	}

}
