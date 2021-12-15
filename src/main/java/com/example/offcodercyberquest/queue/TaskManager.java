package com.example.offcodercyberquest.queue;


// TaskManager is responsible to pull task from the TaskQueue
// and calls perform on it.

public class TaskManager extends Thread{
    TaskQueue queue;
    public TaskManager(){
        queue = TaskQueue.getInstance();
    }
    @Override
    public void run() {
        while(true){
            Task task = TaskQueue.getInstance().getTask();
            if(task != null){
                task.perform();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
