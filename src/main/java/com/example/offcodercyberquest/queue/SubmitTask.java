package com.example.offcodercyberquest.queue;

import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class SubmitTask implements Task {
    @Override
    public void perform() {
        try{
            // TODO logic for submitting code.
            System.out.println("Started Submit task");
            Thread.sleep(2000);
            System.out.println("DONE");
            onComplete();
        }catch (InterruptedException ie){
        }

    }

    @Override
    public void onComplete() {
        // TODO
        // show notification accordingly
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .text("Question Submitted")
                        .hideAfter(Duration.seconds(5))
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
