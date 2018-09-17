package com.cuong;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import com.cuong.controllers.ListsManagerController;
import com.cuong.firebaselisteners.ChangeTitleValueEventListener;
import com.cuong.utils.Constant;
import com.cuong.utils.PathUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {
		initFirebase();
		FXMLLoader fxmlLoader = new FXMLLoader(PathUtils.getViewFile(Constant.VIEW_LIST_MANAGER));
		ListsManagerController listManagerController = new ListsManagerController();
		fxmlLoader.setController(listManagerController);
		Parent root = fxmlLoader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle(Constant.TITLE_INIT);
		primaryStage.show();
		DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_APP_NAME);
		ChangeTitleValueEventListener changeTitleValueEventListener = new ChangeTitleValueEventListener();
		changeTitleValueEventListener.setPrimaryStage(primaryStage);
		rootRef.addValueEventListener(changeTitleValueEventListener);
	}

	public void initFirebase() throws Exception {
		FileInputStream serviceAccount = new FileInputStream(
				new File(PathUtils.getConfigFile("card-admin.json").toURI()));
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://card-29987.firebaseio.com").build();
		FirebaseApp.initializeApp(options);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
