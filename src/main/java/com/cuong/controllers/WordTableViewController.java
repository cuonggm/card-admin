package com.cuong.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.cuong.listeners.WordTableViewEventListener;
import com.cuong.utils.C;
import com.cuong.viewmodels.Word;

import javafx.application.Platform;
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

public class WordTableViewController implements Initializable {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(WordTableViewController.class.getName());

	private WordTableViewEventListener wordTableViewEventListener;

	public WordTableViewEventListener getWordTableViewEventListener() {
		return wordTableViewEventListener;
	}

	public void setWordTableViewEventListener(WordTableViewEventListener wordTableViewEventListener) {
		this.wordTableViewEventListener = wordTableViewEventListener;
	}

	public void refresh() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				tableView.refresh();
			}
		});

	}

	public void addItem(Word word) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				tableView.getItems().add(word);
			}
		});

	}

	public void addOrUpdate(Word word) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (!updateItem(word)) {
					addItem(word);
				}
			}
		});

	}

	public boolean updateItem(Word word) {
		for (Word item : tableView.getItems()) {
			if (item.getId().equals(word.getId())) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						word.cloneTo(item);
						tableView.refresh();
					}
				});

				return true;
			}
		}
		return false;
	}

	public void removeItem(Word word) {
		LOGGER.info("Start remove word from table view");
		for (Word item : tableView.getItems()) {
			if (item.getId().equals(word.getId())) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						tableView.getItems().remove(item);
						LOGGER.info("Removed item");
						tableView.refresh();
					}
				});
				break;
			}
		}
	}

	@FXML
	private TableView<Word> tableView;

	@FXML
	private TableColumn<Word, String> idColumn;

	@FXML
	private TableColumn<Word, String> kanjiColumn;

	@FXML
	private TableColumn<Word, String> hiraganaColumn;

	@FXML
	private TableColumn<Word, String> meaningColumn;

	@FXML
	private TableColumn<Word, String> amHanVietColumn;

	@FXML
	private TableColumn<Word, Date> createdAtColumn;

	@FXML
	private TableColumn<Word, Date> updatedAtColumn;

	@FXML
	private TableColumn<Word, String> deleteColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setEditable(true);

		initIdColumn();
		initKanjiColumn();
		initHiraganaColumn();
		initMeaningColumn();
		initAmHanVietColumn();
		initCreatedAtColumn();
		initUpdatedAtColumn();
		initDeleteColumn();

		tableView.setItems(FXCollections.observableArrayList());
	}

	private void initDeleteColumn() {
		deleteColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Word, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Word, String> param) {
						return new SimpleStringProperty(C.Title.DELETE);
					}
				});
		deleteColumn.setCellFactory(new Callback<TableColumn<Word, String>, TableCell<Word, String>>() {

			@Override
			public TableCell<Word, String> call(TableColumn<Word, String> param) {
				TableCell<Word, String> tableCell = new TableCell<Word, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
							return;
						}
						Button button = new Button(C.Title.DELETE);
						button.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								if (wordTableViewEventListener != null) {
									int row = getTableRow().getIndex();
									Word word = tableView.getItems().get(row);
									wordTableViewEventListener.onDeleteButtonClicked(word);
								}
							}
						});
						setGraphic(button);
						button.setPrefWidth(getTableColumn().getPrefWidth());
					}
				};
				return tableCell;
			}
		});
	}

	private void initUpdatedAtColumn() {
		updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.UPDATED_AT));
	}

	private void initCreatedAtColumn() {
		createdAtColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.CREATED_AT));
	}

	private void initAmHanVietColumn() {
		amHanVietColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.AM_HAN_VIET));
		amHanVietColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		amHanVietColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Word, String>>() {

			@Override
			public void handle(CellEditEvent<Word, String> event) {
				if (wordTableViewEventListener != null) {
					int row = event.getTablePosition().getRow();
					Word word = tableView.getItems().get(row);
					wordTableViewEventListener.onAmHanVietEditCommit(word, event.getOldValue(), event.getNewValue());
				}
			}
		});
	}

	private void initMeaningColumn() {
		meaningColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.MEANING));
		meaningColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		meaningColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Word, String>>() {

			@Override
			public void handle(CellEditEvent<Word, String> event) {
				if (wordTableViewEventListener != null) {
					int row = event.getTablePosition().getRow();
					Word word = tableView.getItems().get(row);
					wordTableViewEventListener.onMeaningEditCommit(word, event.getOldValue(), event.getNewValue());
				}
			}
		});
	}

	private void initHiraganaColumn() {
		hiraganaColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.HIRAGANA));
		hiraganaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hiraganaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Word, String>>() {

			@Override
			public void handle(CellEditEvent<Word, String> event) {
				if (wordTableViewEventListener != null) {
					int row = event.getTablePosition().getRow();
					Word word = tableView.getItems().get(row);
					wordTableViewEventListener.onHiraganaEditCommit(word, event.getOldValue(), event.getNewValue());
				}
			}
		});
	}

	private void initKanjiColumn() {
		kanjiColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.KANJI));
		kanjiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		kanjiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Word, String>>() {

			@Override
			public void handle(CellEditEvent<Word, String> event) {
				if (wordTableViewEventListener != null) {
					int row = event.getTablePosition().getRow();
					Word word = tableView.getItems().get(row);
					wordTableViewEventListener.onKanjiEditCommit(word, event.getOldValue(), event.getNewValue());
				}
			}
		});
	}

	private void initIdColumn() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(C.ViewModelProperty.Word.ID));
	}

}
