package com.cuong.firebaselisteners;

import java.util.logging.Logger;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.stage.Stage;

public class ChangeTitleValueEventListener implements ValueEventListener {

	private static final Logger LOGGER = Logger.getLogger(ChangeTitleValueEventListener.class.getName());

	private Stage primaryStage;

	@Override
	public void onDataChange(DataSnapshot snapshot) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				LOGGER.info("key: " + snapshot.getKey());
				LOGGER.info("value: " + snapshot.getValue().toString());
				if (primaryStage == null) {
					LOGGER.severe("primaryStage is null");
					return;
				} else {
					primaryStage.setTitle(snapshot.getValue().toString());
				}
			}
		});
	}

	@Override
	public void onCancelled(DatabaseError error) {

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
