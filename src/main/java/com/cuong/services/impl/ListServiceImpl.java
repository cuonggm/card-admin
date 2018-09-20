package com.cuong.services.impl;

import java.util.logging.Logger;

import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.eventhandlers.ListItemEventHandler;
import com.cuong.models.List;
import com.cuong.services.ListService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ListServiceImpl implements ListService {

	private static final Logger LOGGER = Logger.getLogger(ListServiceImpl.class.getName());

	private ListDAO listDAO = new ListDAOImpl();
	private WordDAO wordDAO = new WordDAOImpl();

	@Override
	public void setItemEventHandler(ListItemEventHandler handler) {
		if (handler == null) {
			LOGGER.severe("List Item Event Handler is null");
			return;
		}

		listDAO.addChildEventListener(new ChildEventListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				handler.onListAdded(snapshot.getValue(List.class));
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				handler.onListChanged(snapshot.getValue(List.class));
			}

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				handler.onListRemoved(snapshot.getValue(List.class));
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				handler.onListMoved(snapshot.getValue(List.class));
			}

			@Override
			public void onCancelled(DatabaseError error) {
				handler.onListCancelled(error.getDetails());
			}
		});
	}

	@Override
	public void removeItemEventHandler() {
		listDAO.removeChildEventListener();
	}

	@Override
	public void save(List list, OnComplete<List> onComplete) {
		listDAO.save(list);
	}

	@Override
	public void delete(String listId, OnComplete<List> onComplete) {
		LOGGER.info("Delete ListID: " + listId);
		listDAO.load(listId, new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				List list = snapshot.getValue(List.class);
				for (String wordId : list.getWordIDs()) {
					wordDAO.delete(wordId);
				}
				listDAO.delete(listId, onComplete);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				LOGGER.severe(error.getDetails());
			}
		});
	}

}
