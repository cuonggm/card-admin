package com.cuong.daos;

import com.cuong.models.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public interface ListDAO {

	void loadAll(String listId, ValueEventListener valueEventListener);

	List save(List list);

	List update(List list);

	boolean delete(String listId);

	void addChildEventListener(ChildEventListener childEventListener);

	void removeChildEventListener();

}
