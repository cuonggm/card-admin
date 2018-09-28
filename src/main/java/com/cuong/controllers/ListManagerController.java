package com.cuong.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.listeners.WordTableViewEventListener;
import com.cuong.modelconverters.WordConverter;
import com.cuong.services.SpecifiedListService;
import com.cuong.services.WordService;
import com.cuong.services.impl.SpecifiedListServiceImpl;
import com.cuong.services.impl.WordServiceImpl;
import com.cuong.utils.C;
import com.cuong.utils.PathUtils;
import com.cuong.viewmodels.Word;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ListManagerController
		implements Initializable, WordTableViewEventListener, EntityEventHandler<com.cuong.models.Word> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ListManagerController.class.getName());

	private Stage stage;

	private String listId;

	private WordService wordService = new WordServiceImpl();
	private SpecifiedListService specifiedListService;

	private WordTableViewController wordTableViewController;

	public ListManagerController(Stage stage) {
		this.stage = stage;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
		specifiedListService = new SpecifiedListServiceImpl(listId);
	}

	public void setLeftVBox(Node node) {
		leftVBox.getChildren().clear();
		leftVBox.getChildren().add(node);
	}

	public void setRightVBox(Node node) {
		rightVBox.getChildren().clear();
		rightVBox.getChildren().add(node);
	}

	private void initTableView() {
		try {
			wordTableViewController = new WordTableViewController();
			FXMLLoader loader = new FXMLLoader(PathUtils.getViewFile(C.View.WORD_TABLE_VIEW));
			loader.setController(wordTableViewController);
			TableView<Word> tableView = loader.load();
			setLeftVBox(tableView);
			VBox.setVgrow(tableView, Priority.ALWAYS);
		} catch (IOException e) {

		}
		wordTableViewController.setWordTableViewEventListener(this);
	}

	@FXML
	private BorderPane borderPane;

	@FXML
	private VBox leftVBox;

	@FXML
	private VBox rightVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTableView();

		// listen data for tableview
		specifiedListService.setWordEventHandler(this);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				specifiedListService.setWordEventHandler(null);
			}
		});
	}

	@Override
	public void onKanjiEditCommit(Word word, String oldValue, String newValue) {
		wordService.updateKanji(word.getId(), newValue, null);
	}

	@Override
	public void onHiraganaEditCommit(Word word, String oldValue, String newValue) {
		wordService.updateHiragana(word.getId(), newValue, null);
	}

	@Override
	public void onMeaningEditCommit(Word word, String oldValue, String newValue) {
		wordService.updateMeaning(word.getId(), newValue, null);
	}

	@Override
	public void onAmHanVietEditCommit(Word word, String oldValue, String newValue) {
		wordService.updateAmHanViet(word.getId(), newValue, null);
	}

	@Override
	public void onDeleteButtonClicked(Word word) {
		LOGGER.info("START CLICKED: " + word.getId());
		specifiedListService.deleteWordCascade(word.getId());
	}

	@Override
	public void onEntityAdded(com.cuong.models.Word entity) {
		wordTableViewController.addOrUpdate(WordConverter.getViewModel(entity));
	}

	@Override
	public void onEntityChanged(com.cuong.models.Word entity) {

	}

	@Override
	public void onEntityRemoved(com.cuong.models.Word entity) {
		LOGGER.info("Start Removed Word from table + " + entity.getHiragana());
		wordTableViewController.removeItem(WordConverter.getViewModel(entity));
	}

	@Override
	public void onEntityMoved(com.cuong.models.Word entity) {

	}

	@Override
	public void onEntityCancelled(String error) {

	}

}
