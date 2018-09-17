package com.cuong.services;

import com.cuong.viewmodels.List;

import javafx.scene.control.TableView;

public interface ListService {

	void bind(TableView<List> tableView);

	void unbind();

}
