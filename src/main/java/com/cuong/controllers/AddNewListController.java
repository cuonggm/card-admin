package com.cuong.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import com.cuong.daos.OnComplete;
import com.cuong.models.List;
import com.cuong.services.ListService;
import com.cuong.services.impl.ListServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewListController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger(AddNewListController.class.getName());

	private ListService listService = new ListServiceImpl();

	private void setupEvents() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (nameField.getText().isEmpty()) {
					return;
				}
				addButton.setDisable(true);
				List list = new List();
				list.setName(nameField.getText());
				listService.save(list, new OnComplete<List>() {

					@Override
					public void onSuccess(List object) {
						javafx.application.Platform.runLater(new Runnable() {

							@Override
							public void run() {
								close();
							}
						});
					}

					@Override
					public void onError(String error) {
						LOGGER.severe(error);
					}
				});
			}
		});
	}

	private void close() {
		Stage stage = (Stage) addButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private Button addButton;

	@FXML
	private TextField nameField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupEvents();
	}

}
