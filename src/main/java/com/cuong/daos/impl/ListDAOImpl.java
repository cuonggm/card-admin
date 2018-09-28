package com.cuong.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.List;
import com.cuong.utils.C;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ListDAOImpl extends GenericDAO<String, List> implements ListDAO {

	Map<String, ChildEventListener> wordIDsChildEventListeners = new HashMap<>();

	public Map<String, ChildEventListener> getWordIDsChildEventListeners() {
		return wordIDsChildEventListeners;
	}

	public void setWordIDsChildEventListeners(Map<String, ChildEventListener> wordIDsChildEventListeners) {
		this.wordIDsChildEventListeners = wordIDsChildEventListeners;
	}

	@Override
	public DatabaseReference getRootRef() {
		return FirebaseDatabase.getInstance().getReference().child(C.Ref.LISTS);
	}

	@Override
	public void listenWordIDsOf(String listId, EntityEventHandler<String> wordIDEventHandler) {
		if (wordIDEventHandler == null) {
			return;
		}
		ChildEventListener childEventListener = new ChildEventListener() {

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				String wordId = snapshot.getValue(String.class);
				wordIDEventHandler.onEntityRemoved(wordId);
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				String wordId = snapshot.getValue(String.class);
				wordIDEventHandler.onEntityMoved(wordId);
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				String wordId = snapshot.getValue(String.class);
				wordIDEventHandler.onEntityChanged(wordId);
			}

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				String wordId = snapshot.getValue(String.class);
				wordIDEventHandler.onEntityAdded(wordId);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				wordIDEventHandler.onEntityCancelled(error.getMessage());
			}
		};
		getWordIDsChildEventListeners().put(listId, childEventListener);
		getRootRef().child(listId).child(C.Ref.Lists.WORD_IDs).addChildEventListener(childEventListener);
	}

	@Override
	public void removeListenWordIDsOf(String listId) {
		ChildEventListener childEventListener = getWordIDsChildEventListeners().get(listId);
		if (childEventListener != null) {
			getRootRef().child(listId).child(C.Ref.Lists.WORD_IDs).removeEventListener(childEventListener);
		}
	}

	@Override
	public void loadWordIDsOf(String listId, OnComplete<java.util.List<String>> onComplete) {
		getRootRef().child(listId).child(C.Ref.Lists.WORD_IDs).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				java.util.List<String> wordIDs = new ArrayList<>();
				for (DataSnapshot child : snapshot.getChildren()) {
					wordIDs.add(child.getValue(String.class));
				}
				onComplete.onSuccess(wordIDs);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				onComplete.onError(error.getMessage());
			}
		});
	}

	@Override
	public void updateName(String id, String name, OnComplete<List> onComplete) {
		Map<String, Object> updateValue = new HashMap<>();
		updateValue.put(C.ModelProperty.List.NAME, name);
		getRootRef().child(id).updateChildren(updateValue, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (onComplete != null) {
					if (error == null) {
						getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								List list = snapshot.getValue(List.class);
								onComplete.onSuccess(list);
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
	public void deleteWordId(String listId, String wordId, OnComplete<String> onComplete) {
		getRootRef().child(listId).runTransaction(new Handler() {

			@Override
			public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
				if (onComplete != null) {
					if (error == null) {
						onComplete.onSuccess(wordId);
					} else {
						onComplete.onError(error.getMessage());
					}
				}
			}

			@Override
			public Result doTransaction(MutableData currentData) {
				if (currentData == null) {
					return Transaction.success(currentData);
				}
				List list = currentData.getValue(List.class);
				LOGGER.info("size = " + list.getWordIDs().size());
				for (int i = 0; i < list.getWordIDs().size(); i++) {
					if (list.getWordIDs().get(i).equals(wordId)) {
						list.getWordIDs().remove(i);
						i--;
					}
				}
				LOGGER.info("size = " + list.getWordIDs().size());
				currentData.setValue(list);
				return Transaction.success(currentData);
			}
		});
	}

}
