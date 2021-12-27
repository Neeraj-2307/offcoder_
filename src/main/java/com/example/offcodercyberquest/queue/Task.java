package com.example.offcodercyberquest.queue;
/*
*
*
* Providing interface for the main logic of queue that has to be displayed.
*
*
* */
public interface Task {

    // must override to add logic for the task to be performed.
    void perform();
    // Override for what to do after task is completed
    // eg : show notification.
    void onComplete();


    // overide to set priority of child class/task.
    TaskPriorities getPriority();
    String getContestId();
    String getProblemId();
}
