package com.cuong.daos.impl;

import com.cuong.daos.ListDAO;
import com.cuong.models.List;
import com.cuong.utils.C;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListDAOImpl extends GenericDAO<String, List> implements ListDAO {

	@Override
	public DatabaseReference getRootRef() {
		return FirebaseDatabase.getInstance().getReference().child(C.Ref.LISTS);
	}

}
