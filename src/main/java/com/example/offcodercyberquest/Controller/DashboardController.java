package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/*
*
* Start SCreen with all flows
*
* */
public class DashboardController {

    @FXML
    private Button contest_button;

    @FXML
    private Button problem_button;

    @FXML
    private Button logout_button;

    @FXML
    private Button question_button;

    @FXML
    private Button queue_button;

    @FXML
    private Button random_button;

    @FXML
    private Button stats_button;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void load_contest(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Contest.fxml");
    }

    @FXML
    void load_problems(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Suggestion.fxml");
    }

    @FXML
    void load_ide(ActionEvent event) throws IOException {
        EditorController.contestId="1610";
        EditorController.problemId="F";
        HelloApplication m = new HelloApplication();
        m.changeScene("editor-view.fxml");
    }

    @FXML
    void load_login(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Signup.fxml");
    }

    @FXML
    void load_question(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Question.fxml");
    }

    @FXML
    void load_queue(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("DisplayQueue.fxml");
    }

    @FXML
    void load_stats(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Stats.fxml");
    }
}
