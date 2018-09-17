package com.cuong.firebaselisteners;

import java.util.logging.Logger;

import com.cuong.modelconverters.ListConverter;
import com.cuong.viewmodels.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import javafx.application.Platform;
import javafx.scene.control.TableView;

public class ListItemChildEventListener implements ChildEventListener {

	private static final Logger LOGGER = Logger.getLogger(ListItemChildEventListener.class.getName());

	private TableView<List> tableView;

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		LOGGER.info("Added list item");
		LOGGER.info("Key: " + snapshot.getKey());
		com.cuong.models.List list = snapshot.getValue(com.cuong.models.List.class);
		List listViewModel = ListConverter.getViewModel(list);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tableView.getItems().add(listViewModel);
			}
		});
		LOGGER.info("List name: " + list.getName());
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		LOGGER.info("Changed list item");
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		LOGGER.info("Removed list item");
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		LOGGER.info("Moved list item");
	}

	@Override
	public void onCancelled(DatabaseError error) {
		LOGGER.info("Cancelled list item");
	}

	public TableView<List> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<List> tableView) {
		this.tableView = tableView;
	}

}
