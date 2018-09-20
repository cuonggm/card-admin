package com.cuong.daos;

import com.cuong.models.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public interface ListDAO {

	void load(String listId, ValueEventListener valueEventListener);

	List save(List list);

	List update(List list);

	void delete(String listId, OnComplete<List> onComplete);

	void addChildEventListener(ChildEventListener childEventListener);

	void removeChildEventListener();

}
