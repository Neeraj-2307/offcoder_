package com.example.offcodercyberquest.queue;

import com.example.offcodercyberquest.Automation;
import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
/*
*
*
* Class to simulate downloading as download have 2 option so 2 constructure will be there
*
*
*
* */
public class DownloadTask implements Task{
    private String problemID;
    private String contestID;

    // constructor if download contest
    public DownloadTask(String cid){
        contestID=cid;
        problemID="NO";
    }
    //constructor to download problem
    public DownloadTask(String id,String cid){
        problemID=id;
        contestID=cid;
    }
    public void perform() {

        System.out.println("Started Download task");

        Automation automation = new Automation();
        String verdict = null;
        try {
            if(problemID.equals("NO"))
                verdict=automation.download(contestID);
            else
                verdict = automation.download(problemID, contestID);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DONE\n" + verdict);
        onComplete();

    }
    //notification
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
