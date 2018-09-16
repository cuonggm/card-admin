package com.cuong.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.cuong.services.ImportFileService;
import com.cuong.services.impl.ImportFileServiceImpl;
import com.cuong.utils.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ListManagerController implements Initializable {

	private void setupEvents() {
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
	private MenuItem menuItemImportFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupEvents();
	}

}
