package com.cuong.daos;

import com.cuong.models.List;
import com.google.firebase.database.ChildEventListener;

public interface ListDAO {

	List save(List list);

	List update(List list);

	void addChildEventListener(ChildEventListener childEventListener);

	void removeChildEventListener();

}
