package com.example.offcodercyberquest.queue;

import com.example.offcodercyberquest.Automation;
import com.example.offcodercyberquest.Beans.Language;
import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;

public class SubmitTask implements Task {
    private String problemID;
    private String contestID;
    private File codefile;
    private Language language;

    private String verdict;

    public SubmitTask(String problemID, String contestID, File codefile, Language language) {
        this.problemID = problemID;
        this.contestID = contestID;
        this.codefile = codefile;
        this.language = language;
    }

    @Override
    public void perform() {
        // TODO logic for submitting code.

        System.out.println("Started Submit task");

        Automation automation = new Automation();
        String verdict = null;
        try {
            verdict = automation.submit(problemID, contestID, codefile, language);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.verdict = verdict;
        System.out.println("DONE\n" + verdict);
        onComplete();
    }

    @Override
    public void onComplete() {
        // TODO
        // show notification accordingly
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .text( contestID + problemID + " was Submitted")
                        .title(verdict)
                        .hideAfter(Duration.INDEFINITE)
                        .darkStyle()
                        .showInformation();
            }
        });
    }

    @Override
    public TaskPriorities getPriority() {
        return TaskPriorities.SUBMIT_TASK;
    }
}
