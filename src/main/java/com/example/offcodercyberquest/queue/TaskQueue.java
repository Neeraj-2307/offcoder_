package com.example.offcodercyberquest.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
// Class to maintain queue of all the tasks added while application is running
// SINGLETON : task can be added from anywhere in the application.
public class TaskQueue {

    static private TaskQueue taskQueue;
    private PriorityQueue<Task> priorityQueue;

    private TaskQueue() {
        // custom comparator for 'Task' objects based on priorities.
        priorityQueue = new PriorityQueue<>((T1, T2) ->
                T1.getPriority().compareTo(T2.getPriority()));
    }
    public static TaskQueue  getInstance(){
        if(taskQueue == null)
            taskQueue = new TaskQueue();
        return taskQueue;
    }

    // method to add task
    public void addTask(Task task){
        System.out.println(task + " added");
        priorityQueue.add(task);
    }

    // pulls task that has most priority
    public Task getTask(){
        return priorityQueue.poll();
    }
    public List<Task> getTaskList(){
        Iterator i= priorityQueue.iterator();
        List<Task> taskList=new ArrayList<>();
        while(i.hasNext()){
            Task t= (Task) i.next();

            taskList.add(t);
        }
        return taskList;
    }
}
