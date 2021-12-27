package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Automation;
import com.example.offcodercyberquest.Beans.Code;
import com.example.offcodercyberquest.Beans.Language;
import com.example.offcodercyberquest.Beans.TestCase;
import com.example.offcodercyberquest.Beans.User;
import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.environments.*;
import com.example.offcodercyberquest.queue.DownloadTask;
import com.example.offcodercyberquest.queue.SubmitTask;
import com.example.offcodercyberquest.queue.TaskQueue;
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
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/*
*
* This editor view is showing a single problem form the already downloaded questions
*
* */
public class EditorController implements Initializable {
    static String contestId,problemId;
    @FXML
    void load_dashboard(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("dashboard.fxml");
    }
    @FXML
    private TextArea codeArea, outputArea, customInputArea;
    @FXML
    private WebView questionView;
    @FXML
    private Button submitButton, runAllButton, compileButton, runButton;
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private TitledPane outputTiledPane, customInputTiledPane;
    public void setIDS(String cid,String pid){
        System.out.println("SETID");
        this.contestId=cid;
        this.problemId=pid;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("INITIALIZED");
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
        try {
            String questionFile = contestId + "_" + problemId + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(new File(".\\questions\\" + questionFile)));
            StringBuffer buffer = new StringBuffer();
            String x = "";
            while((x = reader.readLine()) != null){
                buffer.append(x).append("\n");
            }
            questionView.getEngine().loadContent(buffer.toString());
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void onSubmit(ActionEvent e) throws IOException {
        CodeFileHandler codeFileHandler = new CodeFileHandler(fetchCode());
        File file = codeFileHandler.createFile();
        System.out.println(contestId+problemId);

        SubmitTask submitTask = new SubmitTask(contestId, problemId, file, getSelectedLanguage());
        TaskQueue.getInstance().addTask(submitTask);
    }

    private void setDefaultCode() {
        //TODO
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

    public void onRunAll(ActionEvent e) throws IOException {
        long milliseconds = 10 * 1000;
        // TODO;
        // fetch problem tc..
        TestCase originalTC = fetchCustomInput();
        Code code = fetchCode();
        String op = runUtil(code,  milliseconds, originalTC);

        // Display output.
        outputArea.setText(op);
        expandTiledPane(outputTiledPane);
    }


    public void onCompile(ActionEvent e) throws IOException {
        System.out.println("COMPILED");
        Code code = fetchCode();

        String output = compileUtil(code);
        outputArea.setText(output);
        expandTiledPane(outputTiledPane);
    }

    // helper method for onRun
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

    public String getIDS() {

        return this.contestId + this.problemId;
    }
}