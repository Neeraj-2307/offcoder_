package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Beans.Code;
import com.example.offcodercyberquest.Beans.Language;
import com.example.offcodercyberquest.Beans.TestCase;
import com.example.offcodercyberquest.environments.*;
import com.example.offcodercyberquest.utils.CodeFileHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
    @FXML
    private TextArea codeArea, outputArea, customInputArea;
    @FXML
    private TextFlow metaFlow, problemFlow;
    @FXML
    private Button submitButton, runAllButton, compileButton, runButton;
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private TitledPane outputTiledPane, customInputTiledPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuestionView();
        initLanguageChoiceBox();
        System.out.println(languageChoiceBox.getValue());
    }

    void initLanguageChoiceBox() {
        // Initialize choice box with default lang & available languages.
        List<String> langList = new ArrayList<>();
        for (int i = 0; i < Language.values().length; i++) {
            langList.add(String.valueOf(Language.values()[i]));
        }
        languageChoiceBox.setItems(FXCollections.observableList(langList));

        languageChoiceBox.setValue(String.valueOf(Language.values()[0]));// set Default value
    }

    private Language getSelectedLanguage() {
        String lang = languageChoiceBox.getSelectionModel().getSelectedItem();
        return Language.valueOf(lang);
    }

    public void setQuestionView() {
        //TODO Fetch Question from file and display;

        Text meta = new Text("""
                J. Robot Factory
                time limit per test : 1 second
                memory limit per test : 256 megabytes
                input : standard input
                output : standard output
                """);

        metaFlow.setPadding(new Insets(10, 10, 10, 10));
        metaFlow.setTextAlignment(TextAlignment.CENTER);

        Text problemDescription = new Text("""
                You have received data from a Bubble bot. You know your task is to make factory facilities, but before you even start, you need to know how big the factory is and how many rooms it has. When you look at the data you see that you have the dimensions of the construction, which is in rectangle shape: N x M.
                Then in the next N lines you have M numbers. These numbers represent factory tiles and they can go from 0 to 15. Each of these numbers should be looked in its binary form. Because from each number you know on which side the tile has walls. For example number 10 in it's binary form is 1010, which means that it has a wall from the North side, it doesn't have a wall from the East, it has a wall on the South side and it doesn't have a wall on the West side. So it goes North, East, South, West.
                It is guaranteed that the construction always has walls on it's edges. The input will be correct.
                Your task is to print the size of the rooms from biggest to smallest.""");

        problemFlow.setPadding(new Insets(10, 10, 10, 10));
        metaFlow.getChildren().add(meta);
        problemFlow.getChildren().add(problemDescription);

    }


    public void onSubmit(ActionEvent e) {
        //TODO submit util
    }

    private void setDefaultCode() {
        // TODO ..........
        //codeArea.setText(user.getDefaultCode(Language.JAVA));
    }

    private Code fetchCode() {
        return new Code(codeArea.getText(), getSelectedLanguage());
    }

    // runs code and sets output area.
    public void onRun(ActionEvent e) throws IOException {
        System.out.println("EXECUTED");

        // TODO : handle time constrains while running.
        long milliseconds = 10 * 1000;

        // run custom testcase.
        TestCase customTC = fetchCustomInput();
        Code code = fetchCode();
        String op = runUtil(code,  milliseconds, customTC);

        // Display output.
        outputArea.setText(op);
        expandTiledPane(outputTiledPane);
    }

    public void onRunAll(ActionEvent e) {
        System.out.println("RAN ALL TC");
        System.out.println(fetchCode().getCode());
        //TODO compile & run then set text in output Area
    }


    public void onCompile(ActionEvent e) throws IOException {
        // TODO
        System.out.println("COMPILED");
        Code code = fetchCode();

        String output = compileUtil(code);
        outputArea.setText(output);
        expandTiledPane(outputTiledPane);
    }
    public String runUtil(Code code, long milliseconds, TestCase testcase) throws IOException {
        CodeFileHandler fileHandler = new CodeFileHandler(code);
        // creates a temp file in local directory.
        File codeFile = fileHandler.createFile();

        // calls appropriate env to run.
        // run method implicitly calls compile.
        try {
            switch (code.getLanguage()) {
                case JAVA -> {
                    JavaEnvironment env = new JavaEnvironment();
                    return env.run(codeFile, testcase, milliseconds);
                }

                case PYTHON -> {
                    PythonEnvironment env = new PythonEnvironment();
                    return env.run(codeFile, testcase, milliseconds);
                }
                case CPP -> {
                    CppEnvironment env = new CppEnvironment();
                    return  env.run(codeFile, testcase, milliseconds);
                }
                case JAVASCRIPT -> {
                    JavaScriptEnvironment env = new JavaScriptEnvironment();
                    return env.run(codeFile, testcase, milliseconds);
                }
                default -> {
                    return "Something went wrong\n";
                }
            }

        } catch (TimeLimitExceededException tle) {
            return "TLE : ";
        } catch (InterruptedException ie) {
            return "Something went wrong\n";
        }

    }


    // helper method for onCompile method.
    // return output of type string after compile.
    private String compileUtil(Code code) throws IOException {
        CodeFileHandler fileHandler = new CodeFileHandler(code);
        // creates a file of code in temp directory.
        File codeFile = fileHandler.createFile();

        // calls appropriate environment to run code.
        switch (code.getLanguage()) {
            case JAVA -> {
                JavaEnvironment env = new JavaEnvironment();
                return env.compile(codeFile);
            }
            case CPP -> {
                CppEnvironment env = new CppEnvironment();
                return env.compile(codeFile);
            }
            default -> {
                return "Something went wrong";
            }
        }

    }

    // reads custom test case and returns a TestCase object.
    private TestCase fetchCustomInput() {
        return new TestCase(customInputArea.getText(), null);
    }

    // expands the passed tiles pane.
    public void expandTiledPane(TitledPane titledPane) {
        titledPane.setExpanded(true);
    }

    public void setOutputArea() {
        outputArea.setText("""
                TODO
                """);
    }
}