package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.UserAuth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button button;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup_button;

    @FXML
    private TextField username;

    @FXML
    private Label wronglogin;

    @FXML
    public void signup_call(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Signup.fxml");
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        checkLogin();
    }
    public void checkLogin() throws IOException, ClassNotFoundException, InterruptedException {
        HelloApplication m = new HelloApplication();
        if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
        }
        else {
            wronglogin.setText("Success!");
            UserAuth newUser = new UserAuth();
            String user_name = username.getText();
            String user_password = password.getText();
            int status = newUser.deserializeUser(user_name,user_password);
            if(status == -1){
                wronglogin.setText("User name not registered");
            }
            else if(status == 0){
                wronglogin.setText("Wrong user name or password");
            }
            else {
                //CHANGING SCENE
                m.changeScene("Dashboard.fxml");
            }
        }
    }

}
