package com.cuong.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.cuong.listeners.ListTableViewEventListener;
import com.cuong.utils.C;
import com.cuong.viewmodels.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class ListTableViewController implements Initializable {

	@SuppressWarnings("unused")
	private static Logger LOGGER = Logger.getLogger(ListTableViewController.class.getName());

	private ListTableViewEventListener listTableViewEventListener;

	public ListTableViewEventListener getListTableViewEventListener() {
		return listTableViewEventListener;
	}

	public void setListTableViewEventListener(ListTableViewEventListener listTableViewEventListener) {
		this.listTableViewEventListener = listTableViewEventListener;
	}

	@FXML
	private TableView<List> tableView;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> idColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> nameColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, Date> createdAtColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, Date> updatedAtColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, Long> numberOfWordsColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> showWordsColumn;

	@FXML
	private TableColumn<com.cuong.viewmodels.List, String> deleteColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setEditable(true);

		initIdColumn();
		initNameColumn();
		initCreatedAtColumn();
		initUpdatedAtColumn();
		initNumberOfWordsColumn();
		initShowWordsColumn();
		initDeleteColumn();

		tableView.setItems(FXCollections.observableArrayList());

	}

	public TableView<List> getTableView() {
		return this.tableView;
	}

	public void refresh() {
		getTableView().refresh();
	}

	public void addItem(List list) {
		tableView.getItems().add(list);
	}

	public void update(List list) {
		for (List item : getTableView().getItems()) {
			if (item.getId().equals(list.getId())) {
				list.cloneTo(item);
			}
		}
		tableView.refresh();
	}

	public void removeItem(String id) {
		for (List item : tableView.getItems()) {
			if (item.getId().equals(id)) {
				tableView.getItems().remove(item);
			}
		}
	}

	private void initDeleteColumn() {
		deleteColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<List, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<List, String> param) {
						return new SimpleStringProperty(C.Title.DELETE);
					}
				});
		deleteColumn.setCellFactory(new Callback<TableColumn<List, String>, TableCell<List, String>>() {

			@Override
			public TableCell<List, String> call(TableColumn<List, String> param) {
				TableCell<List, String> tableCell = new TableCell<List, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
							return;
						}
						Button button = new Button(C.Title.DELETE);
						button.setPrefSize(deleteColumn.getPrefWidth(), USE_COMPUTED_SIZE);
						button.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								int row = getTableRow().getIndex();
								List list = tableView.getItems().get(row);
								if (listTableViewEventListener != null) {
									listTableViewEventListener.onDeleteButtonClicked(list);
								}
							}
						});
						setText(null);
						setGraphic(button);
					}
				};
				return tableCell;
			}
		});
	}

	private void initShowWordsColumn() {
		showWordsColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<List, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<List, String> param) {
						return new SimpleStringProperty(C.Title.SHOW_WORDS);
					}
				});
		showWordsColumn.setCellFactory(new Callback<TableColumn<List, String>, TableCell<List, String>>() {

			@Override
			public TableCell<List, String> call(TableColumn<List, String> param) {
				TableCell<List, String> tableCell = new TableCell<List, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
							setGraphic(null);
							return;
						}
						Button button = new Button(C.Title.SHOW_WORDS);
						button.setPrefSize(showWordsColumn.getPrefWidth(), USE_COMPUTED_SIZE);
						button.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								int row = getTableRow().getIndex();
								List list = tableView.getItems().get(row);
								if (listTableViewEventListener != null) {
									listTableViewEventListener.onShowWordsButtonClicked(list);
								}

							}
						});
						setText(null);
						setGraphic(button);
					}
				};

				return tableCell;
			}
		});
	}

	private void initNumberOfWordsColumn() {
		numberOfWordsColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.NUMBER_OF_WORDS));
	}

	private void initUpdatedAtColumn() {
		updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.UPDATED_AT));
	}

	private void initCreatedAtColumn() {
		createdAtColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.CREATED_AT));
	}

	private void initNameColumn() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.NAME));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<com.cuong.viewmodels.List, String>>() {

			@Override
			public void handle(CellEditEvent<com.cuong.viewmodels.List, String> event) {
				List list = tableView.getItems().get(event.getTablePosition().getRow());
				if (listTableViewEventListener != null) {
					listTableViewEventListener.onNameEditCommit(list, event.getOldValue(), event.getNewValue());
				}
			}
		});
	}

	private void initIdColumn() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.List.ID));
	}

}
