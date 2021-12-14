package com.example.offcodercyberquest;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class QueueDisplay extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load((getClass().getResource("DisplayQueue.fxml")));
		Scene scene=new Scene(root,Color.ALICEBLUE);
		stage.setTitle("pending");
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String args[]) {
		
		launch(args);
	}
	

}
