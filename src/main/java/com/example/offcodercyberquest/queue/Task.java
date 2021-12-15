package com.example.offcodercyberquest.queue;

public interface Task {

    // must override to add logic for the task to be performed.
    void perform();
    // Override for what to do after task is completed
    // eg : show notification.
    void onComplete();


    // overide to set priority of child class/task.
    TaskPriorities getPriority();
}
