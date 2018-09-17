package com.cuong.controllers;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import com.cuong.services.ImportFileService;
import com.cuong.services.ListService;
import com.cuong.services.impl.ImportFileServiceImpl;
import com.cuong.services.impl.ListServiceImpl;
import com.cuong.utils.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ListsManagerController implements Initializable {

	private ListService listService = new ListServiceImpl();

	private void setupTableView() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(Constant.PROPERTY_LIST_ID));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>(Constant.PROPERTY_LIST_NAME));
		createdAtColumn.setCellValueFactory(new PropertyValueFactory<>(Constant.PROPERTY_LIST_CREATED_AT));
		numberOfWordsColumn.setCellValueFactory(new PropertyValueFactory<>(Constant.PROPERTY_LIST_NUMBER_OF_WORDS));
	}

	private void setupEvents() {
		// set list item listener for tableview
		listService.bind(tableView);

		menuItemImportFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				importFile();
			}
		});
	}

	private void importFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new ExtensionFilter(Constant.TITLE_TEXT_FILE, Constant.EXTENSION_TEXT_FILE));
		fileChooser.setTitle(Constant.TITLE_CHOOSE_IMPORT_FILE);
		List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
		ImportFileService importFileService = new ImportFileServiceImpl();
		if (files != null) {
			for (File file : files) {
				importFileService.importFromFile(file);
			}
		}
	}

	@FXML
	private TableView<com.cuong.viewmodels.List> tableView;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> idColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> nameColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, Date> createdAtColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, Long> numberOfWordsColumn;

	@FXML
	private MenuItem menuItemImportFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// setup tables
		setupTableView();

		// setup events
		setupEvents();
	}

}
