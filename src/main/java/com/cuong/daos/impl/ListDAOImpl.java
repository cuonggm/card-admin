package com.cuong.daos.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cuong.daos.ListDAO;
import com.cuong.models.List;
import com.cuong.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListDAOImpl implements ListDAO {

	private DatabaseReference listsRef = FirebaseDatabase.getInstance().getReference().child(Constant.REF_LISTS);

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

}
