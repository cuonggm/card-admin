package com.cuong.daos.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.cuong.daos.ListDAO;
import com.cuong.models.List;
import com.cuong.utils.Constant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListDAOImpl implements ListDAO {

	private DatabaseReference listsRef = FirebaseDatabase.getInstance().getReference().child(Constant.REF_LISTS);
	private ChildEventListener childEventListener;

	@Override
	public void loadAll(String listId, ValueEventListener valueEventListener) {
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
		Map<String, Object> child = new HashMap<>();
		child.put(list.getId(), list);
		listsRef.updateChildrenAsync(child);
		return list;
	}

	@Override
	public boolean delete(String listId) {
		listsRef.child(listId).removeValueAsync();
		return true;
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
