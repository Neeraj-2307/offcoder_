package com.example.offcodercyberquest.queue;

import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class DownloadTask implements Task{

    @Override
    public void perform() {
        try{
            System.out.println("Started Download task");
            // TODO logic for downloading/scraping question or editorial.
            Thread.sleep(2000);
            System.out.println("DONE");
            onComplete();
        }catch (InterruptedException ie){
        }

    }

    @Override
    public void onComplete() {
        // TODO must display info about completed Download task.
        //show notification accordingly
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .text("Downloaded")
                        .hideAfter(Duration.seconds(5))
                        .darkStyle()
                        .showInformation();
            }
        });
    }

    @Override
    public TaskPriorities getPriority() {
        return TaskPriorities.DOWNLOAD_TASK;
    }
}
