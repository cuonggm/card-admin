package com.cuong.services.impl;

import java.util.logging.Logger;
import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.List;
import com.cuong.services.ListService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ListServiceImpl implements ListService {

	private static final Logger LOGGER = Logger.getLogger(ListServiceImpl.class.getName());

	private ListDAO listDAO = new ListDAOImpl();
	private WordDAO wordDAO = new WordDAOImpl();

	@Override
	public void setItemEventHandler(EntityEventHandler<List> handler) {
		if (handler == null) {
			LOGGER.severe("List Item Event Handler is null");
			return;
		}

		listDAO.listenAll(new ChildEventListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				handler.onEntityAdded(snapshot.getValue(List.class));
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				handler.onEntityChanged(snapshot.getValue(List.class));
			}

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				handler.onEntityRemoved(snapshot.getValue(List.class));
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				handler.onEntityMoved(snapshot.getValue(List.class));
			}

			@Override
			public void onCancelled(DatabaseError error) {
				handler.onEntityCancelled(error.getDetails());
			}
		});
	}

	@Override
	public void removeItemEventHandler() {
		listDAO.removeListenAll();
	}

	@Override
	public void save(List list, OnComplete<List> onComplete) {
		listDAO.save(list, onComplete);
	}

	@Override
	public void delete(String listId, OnComplete<List> onComplete) {
		listDAO.load(listId, new OnComplete<List>() {

			@Override
			public void onSuccess(List object) {
				for (String wordId : object.getWordIDs()) {
					wordDAO.delete(wordId, null);
				}
				listDAO.delete(listId, onComplete);
			}

			@Override
			public void onError(String error) {
				onComplete.onError(error);
			}
		});
	}

}
