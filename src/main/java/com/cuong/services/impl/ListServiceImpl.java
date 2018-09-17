package com.cuong.services.impl;

import com.cuong.daos.ListDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.firebaselisteners.ListItemChildEventListener;
import com.cuong.services.ListService;
import com.cuong.viewmodels.List;
import javafx.scene.control.TableView;

public class ListServiceImpl implements ListService {

	private ListDAO listDAO = new ListDAOImpl();
	private ListItemChildEventListener listItemChildEventListener;
	private TableView<List> tableView;

	@Override
	public void bind(TableView<List> tableView) {
		this.tableView = tableView;
		listItemChildEventListener = new ListItemChildEventListener();
		listItemChildEventListener.setTableView(this.tableView);
		listDAO.addChildEventListener(listItemChildEventListener);
	}

	@Override
	public void unbind() {
		listDAO.removeChildEventListener();
	}

}
