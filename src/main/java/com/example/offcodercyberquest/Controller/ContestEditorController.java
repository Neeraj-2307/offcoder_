package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Beans.Code;
import com.example.offcodercyberquest.Beans.Language;
import com.example.offcodercyberquest.Beans.TestCase;
import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.environments.*;
import com.example.offcodercyberquest.queue.SubmitTask;
import com.example.offcodercyberquest.queue.TaskQueue;
import com.example.offcodercyberquest.utils.CodeFileHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/*
*
* A view which will dispaly the contest and take problem index from user and simulte submit and run test cases accordingly
*
* */
public class ContestEditorController implements Initializable {
    public static String contestId;
    static int duration;
    @FXML
    void load_dashboard(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("dashboard.fxml");
    }
    @FXML
    private Text timer;
    @FXML
    private TextField index;
    @FXML
    private TextArea codeArea, outputArea, customInputArea;
    @FXML
    private WebView questionView;
    @FXML
    private Button submitButton, runAllButton, compileButton, runButton,startTime;
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private TitledPane outputTiledPane, customInputTiledPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //it will set the question in webView using file
        setQuestionView();
        //adding languages in drop down
        initLanguageChoiceBox();
        System.out.println(languageChoiceBox.getValue());
        System.out.println(contestId+duration);
    }
    public void setIDS(String cid,int d){
        contestId=cid;
        duration=d;
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
            BufferedReader reader = new BufferedReader(new FileReader(new File(".\\Contests\\"+contestId+".txt")));
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
        if(!startTime.isDisabled())
            timer.setText("start contest for more pressure");
        CodeFileHandler codeFileHandler = new CodeFileHandler(fetchCode());
        File file = codeFileHandler.createFile();

        // TODO HARDCODE

        String problemID =index.getText();
        //

        SubmitTask submitTask = new SubmitTask(contestId, problemID, file, getSelectedLanguage());
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

// timer to set used to check time left and submit only till time
    public void startTimer(ActionEvent actionEvent) {
        startTime.setDisable(true);
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {
            int countdownStarter = duration*3600;

            public void run() {

                timer.setText(String.valueOf(countdownStarter));
                countdownStarter--;
                submitButton.setDisable(false);
                if (countdownStarter < 0) {
                    timer.setText("Time is UP!!!");
                    scheduler.shutdown();
                    startTime.setDisable(false);
                    submitButton.setDisable(true);
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }
}
