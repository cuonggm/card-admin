package com.cuong.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.listeners.ListTableViewEventListener;
import com.cuong.modelconverters.ListConverter;
import com.cuong.services.ListService;
import com.cuong.services.impl.ListServiceImpl;
import com.cuong.utils.C;
import com.cuong.utils.PathUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ListsManagerController
		implements Initializable, EntityEventHandler<com.cuong.models.List>, ListTableViewEventListener {

	private static final Logger LOGGER = Logger.getLogger(ListsManagerController.class.getName());

	private ListService listService = new ListServiceImpl();

	private ListTableViewController listTableViewController;

	private void initTableViewController() {
		try {
			listTableViewController = new ListTableViewController();
			listTableViewController.setListTableViewEventListener(this);
			FXMLLoader loader = new FXMLLoader(PathUtils.getViewFile(C.View.LIST_TABLE_VIEW));
			loader.setController(listTableViewController);
			TableView<com.cuong.viewmodels.List> tableView = loader.load();
			borderPane.setCenter(tableView);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	private void setupEvents() {
		importFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				importFile();
			}
		});

		addNewListMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(PathUtils.getViewFile(C.View.ADD_NEW_LIST));
					AddNewListController addNewListController = new AddNewListController();
					fxmlLoader.setController(addNewListController);
					Parent root = fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.setTitle(C.Title.ADD_NEW_LIST);
					stage.show();
				} catch (IOException e) {
					LOGGER.severe(e.getMessage());
				}
			}
		});
	}

	private void showWords(com.cuong.viewmodels.List list) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(PathUtils.getViewFile(C.View.LIST_MANAGER));
			ListManagerController listManagerController = new ListManagerController();
			listManagerController.setListId(list.getId());
			fxmlLoader.setController(listManagerController);
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1000, 800));
			stage.setTitle(list.getName());
			stage.show();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	private void importFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter(C.Title.TEXT_FILE, C.FileExtension.TEXT_FILE));
		fileChooser.setTitle(C.Title.CHOOSE_IMPORT_FILE);
		List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
		ListService listService = new ListServiceImpl();
		if (files != null) {
			for (File file : files) {
				listService.importTextFile(file);
			}
		}
	}

	@FXML
	private BorderPane borderPane;

	@FXML
	private MenuItem importFileMenuItem;

	@FXML
	private MenuItem addNewListMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// init tables
		initTableViewController();

		// setup events
		setupEvents();

		// bind data to UI
		listService.listenAll(this);
	}

	@Override
	public void onEntityAdded(com.cuong.models.List list) {
		LOGGER.info("Added list item");
		com.cuong.viewmodels.List listViewModel = ListConverter.getViewModel(list);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				listTableViewController.addItem(listViewModel);
			}
		});
	}

	@Override
	public void onEntityChanged(com.cuong.models.List list) {
		LOGGER.info("Changed list item");
		com.cuong.viewmodels.List viewModelList = ListConverter.getViewModel(list);
		listTableViewController.update(viewModelList);
	}

	@Override
	public void onEntityRemoved(com.cuong.models.List list) {
		LOGGER.info("Removed list item");
		listTableViewController.removeItem(list.getId());
	}

	@Override
	public void onEntityMoved(com.cuong.models.List list) {
		LOGGER.info("Moved list item");
		listTableViewController.refresh();
	}

	@Override
	public void onEntityCancelled(String error) {
		LOGGER.info("Cancelled list item");
		listTableViewController.refresh();
	}

	@Override
	public void onNameEditCommit(com.cuong.viewmodels.List list, String oldValue, String newValue) {
		LOGGER.info("List ID: " + list.getId());
		LOGGER.info("Old Value: " + oldValue);
		LOGGER.info("New Value: " + newValue);
		listService.updateName(list.getId(), newValue, null);
	}

	@Override
	public void onShowWordsButtonClicked(com.cuong.viewmodels.List list) {
		LOGGER.info("List ID: " + list.getId());
		showWords(list);
	}

	@Override
	public void onDeleteButtonClicked(com.cuong.viewmodels.List list) {
		LOGGER.info("List ID: " + list.getId());
		listService.deleteCascade(list.getId(), null);
	}

}
