package com.cuong.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.modelconverters.ListConverter;
import com.cuong.services.ImportFileService;
import com.cuong.services.ListService;
import com.cuong.services.impl.ImportFileServiceImpl;
import com.cuong.services.impl.ListServiceImpl;
import com.cuong.utils.C;
import com.cuong.utils.PathUtils;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ListsManagerController implements Initializable, EntityEventHandler<com.cuong.models.List> {

	private static final Logger LOGGER = Logger.getLogger(ListsManagerController.class.getName());

	private ListService listService = new ListServiceImpl();

	private void setupTableView() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.ID));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.NAME));
		createdAtColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.CREATED_AT));
		numberOfWordsColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.NUMBER_OF_WORDS));
	}

	private void setupEvents() {
		menuItemImportFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				importFile();
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deleteSelectedLists();
			}
		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {
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

		showButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ObservableList<com.cuong.viewmodels.List> lists = tableView.getSelectionModel().getSelectedItems();
				if (lists != null) {
					for (com.cuong.viewmodels.List list : lists) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(PathUtils.getViewFile(C.View.LIST_MANAGER));
							ListManagerController listManagerController = new ListManagerController();
							listManagerController.setListId(list.getId());
							fxmlLoader.setController(listManagerController);
							Parent root = fxmlLoader.load();
							Stage stage = new Stage();
							stage.setScene(new Scene(root));
							stage.setTitle(list.getName());
							stage.show();
						} catch (IOException e) {
							LOGGER.severe(e.getMessage());
						}
					}
				}
			}
		});
	}

	private void deleteSelectedLists() {
		ObservableList<com.cuong.viewmodels.List> selectedItems = tableView.getSelectionModel().getSelectedItems();
		if (selectedItems != null) {
			for (com.cuong.viewmodels.List list : selectedItems) {
				listService.delete(list.getId(), new OnComplete<com.cuong.models.List>() {

					@Override
					public void onSuccess(com.cuong.models.List object) {
						LOGGER.info("Deleted list: " + object.getName());
					}

					@Override
					public void onError(String error) {
						LOGGER.severe("Cannot delete list: " + error);
					}
				});
			}
		}
	}

	private void importFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter(C.Title.TEXT_FILE, C.FileExtension.TEXT_FILE));
		fileChooser.setTitle(C.Title.CHOOSE_IMPORT_FILE);
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

	@FXML
	private Button deleteButton;

	@FXML
	private Button addButton;

	@FXML
	private Button showButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// setup tables
		setupTableView();

		// setup events
		setupEvents();

		// bind data to UI
		listService.setItemEventHandler(this);
	}

	@Override
	public void onEntityAdded(com.cuong.models.List list) {
		LOGGER.info("Added list item");
		com.cuong.viewmodels.List listViewModel = ListConverter.getViewModel(list);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tableView.getItems().add(listViewModel);
			}
		});
	}

	@Override
	public void onEntityChanged(com.cuong.models.List list) {
		LOGGER.info("Changed list item");
	}

	@Override
	public void onEntityRemoved(com.cuong.models.List list) {
		LOGGER.info("Removed list item");
		for (com.cuong.viewmodels.List currentList : tableView.getItems()) {
			if (currentList.getId().equals(list.getId())) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						tableView.getItems().remove(currentList);
					}
				});
			}
		}
	}

	@Override
	public void onEntityMoved(com.cuong.models.List list) {
		LOGGER.info("Moved list item");
	}

	@Override
	public void onEntityCancelled(String error) {
		LOGGER.info("Cancelled list item");
	}

}
