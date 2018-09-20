package com.cuong;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;
import com.cuong.controllers.ListsManagerController;
import com.cuong.utils.C;
import com.cuong.utils.PathUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {
		LOGGER.info("Init Firebase");
		initFirebase();
		FXMLLoader fxmlLoader = new FXMLLoader(PathUtils.getViewFile(C.View.LISTS_MANAGER));
		ListsManagerController listManagerController = new ListsManagerController();
		fxmlLoader.setController(listManagerController);
		Parent root = fxmlLoader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle(C.Title.INIT);
		primaryStage.show();
		DatabaseReference appNameRef = FirebaseDatabase.getInstance().getReference().child(C.Ref.APP_NAME);
		appNameRef.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						primaryStage.setTitle(snapshot.getValue(String.class));
					}
				});

			}

			@Override
			public void onCancelled(DatabaseError error) {
				LOGGER.info("onCancelled");
			}
		});
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
