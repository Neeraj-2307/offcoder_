package com.example.offcodercyberquest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.Objects;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load((Objects.requireNonNull(getClass().getResource("Suggestion.fxml"))));
		Scene scene=new Scene(root,Color.ALICEBLUE);
		stage.setTitle("pending");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
