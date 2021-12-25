package com.example.offcodercyberquest;

import com.example.offcodercyberquest.queue.TaskManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
* OFFCODER
* Platform simluated for offline purposes: CODEFORCES
*
* Project start
* project Structure:
* 1. A directory of question (storing questions ), Contest ( storing questions) , User files( storing user authdetails and stats)
* 2. Beans: to simply the work of getting and setting methods working as dto
* 3. Controllers hold the logic to  xmls working and displaying UI
* 4.Enviournments: this will hold support for differnet kind of programming languages that user can cde in
* 5. queue: this the main logic and thread handler where each task is simluated with an priority and check of intenet control
* 6. Scrapper : Our own scrapper the will get question and contest data from the codeforces handle on presence of internet
* 7. utils : include code reader write to seperate logic
* 8.: resources will contaion all documeneted fxml files that are needed.
*
*
*
*
*
*

*
*
*
* */

public class HelloApplication extends Application {
    private static Stage stg;
    private TaskManager manager;
    @Override
    public void start(Stage stage) {
        try {
            stg = stage;
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root,685,475);
            stage.setScene(scene);
            stage.show();

            // task manager thread
            manager = new TaskManager();
            manager.setName("TASK MANAGER THREAD");
            manager.start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //FUNCTION TO CHANGE SCENE
    public void changeScene(String fxml) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public static void main(String[] args) {launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Chrometon.getDriverInstance().quit();
    }
}