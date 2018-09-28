package com.cuong;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {
		initFirebase();
	}

	public void initFirebase() throws Exception {
		LOGGER.info("Init Firebase");
		String projectPath = System.getProperty("user.dir");
		String configPath = projectPath + "/card-admin.json";
		FileInputStream serviceAccount = new FileInputStream(new File(configPath));
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://card-29987.firebaseio.com").build();
		FirebaseApp.initializeApp(options);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
