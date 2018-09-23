package com.cuong.daos.impl;

import java.util.HashMap;
import java.util.Map;

import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.models.List;
import com.cuong.utils.C;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListDAOImpl extends GenericDAO<String, List> implements ListDAO {

	@Override
	public DatabaseReference getRootRef() {
		return FirebaseDatabase.getInstance().getReference().child(C.Ref.LISTS);
	}

	@Override
	public void updateName(String id, String name, OnComplete<List> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.List.NAME, name);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {
						List list = snapshot.getValue(List.class);
						if (onComplete != null) {
							onComplete.onSuccess(list);
						}

					}

					@Override
					public void onCancelled(DatabaseError error) {
						if (onComplete != null) {
							onComplete.onError(error.getMessage());
						}
					}
				});
			}
		});
	}

}
