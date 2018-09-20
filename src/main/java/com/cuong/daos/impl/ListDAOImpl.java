package com.cuong.daos.impl;

import java.util.Date;
import java.util.logging.Logger;
import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.modelconverters.ListConverter;
import com.cuong.models.List;
import com.cuong.utils.C;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;

public class ListDAOImpl implements ListDAO {

	private static final Logger LOGGER = Logger.getLogger(ListDAOImpl.class.getName());

	private DatabaseReference listsRef = FirebaseDatabase.getInstance().getReference().child(C.Ref.LISTS);
	private ChildEventListener childEventListener;

	@Override
	public void load(String listId, ValueEventListener valueEventListener) {
		listsRef.child(listId).addListenerForSingleValueEvent(valueEventListener);
	}

	@Override
	public List save(List list) {
		DatabaseReference listRef = listsRef.push();
		list.setId(listRef.getKey());
		list.setCreatedAt(Long.valueOf(new Date().getTime()));
		listRef.setValueAsync(list);
		return list;
	}

	@Override
	public List update(List list) {
		listsRef.child(list.getId()).runTransaction(new Handler() {
			@Override
			public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
				if (error != null) {
					LOGGER.severe(error.getDetails());
				} else {
					LOGGER.info("List update complete");
				}
			}

			@Override
			public Result doTransaction(MutableData currentData) {
				if (currentData == null) {
					return Transaction.success(currentData);
				}
				List currentList = currentData.getValue(List.class);
				ListConverter.copy(currentList, list);
				return Transaction.success(currentData);
			}
		});
		return list;
	}

	@Override
	public void delete(String listId, OnComplete<List> onComplete) {
		listsRef.child(listId).removeValue(new CompletionListener() {
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error == null) {
					onComplete.onSuccess(new List());
				} else {
					onComplete.onError(error.getMessage());
				}
			}
		});
	}

	@Override
	public void addChildEventListener(ChildEventListener childEventListener) {
		this.childEventListener = childEventListener;
		listsRef.addChildEventListener(childEventListener);
	}

	@Override
	public void removeChildEventListener() {
		if (childEventListener != null) {
			listsRef.removeEventListener(childEventListener);
		}
	}

}
