package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.example.demo.DemoApplication.errorState;

public class UserTasks extends LinkedHashMap<String, Tasks> {

    public TaskState CREATE(String task){
        TaskState st;
        //int userPosition = MyArrays.getPosition(userTasks,Tasks.activeUser);

        if (this.containsKey(AllTasks.getActiveUser())) {
            Tasks tasks = this.get(AllTasks.getActiveUser());
            st = tasks.CREATE(task);
        } else {
            Tasks newUserTasks = new Tasks();
            st = newUserTasks.CREATE(task);
            if (st == TaskState.CREATED){ this.put(AllTasks.getActiveUser(),newUserTasks);}
        }

        return st;
    }

    public ArrayList<String> LIST(String userForRead){
        ArrayList<String> TasksForRead = new ArrayList<String>();
        if (this.containsKey(userForRead)) {
            Tasks newTasks = this.get(userForRead);
            TasksForRead = newTasks.LIST_USERTASK();
        } else {
            TasksForRead.add(errorState.name());
        }
        return TasksForRead;
    }

    public TaskState ACTION(String task, UserAction action){
        TaskState st;
        if (this.containsKey(AllTasks.getActiveUser())) {
            Tasks newTasks = this.get(AllTasks.getActiveUser());
            st = newTasks.ACTION_USERTASK(task,action);
        } else {
            if (AllTasks.allActiveTasks.contains(task)) {
                st = TaskState.ACCESS_DENIED;
            } else {
                st = TaskState.ERROR;
            }
        }
        return st;
    }

}
