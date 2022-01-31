package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.demo.DemoApplication.errorState;

public class Tasks extends LinkedHashMap<String, TaskState> {

    public TaskState CREATE(String task) {
        TaskState st;
        if  (!this.containsKey(task)){
            st = TaskState.CREATED;
            this.put(task,st);
        } else {
            st = errorState;
        }
        return st;
    }

    public ArrayList<String> LIST_USERTASK() {
        ArrayList<String> activeTasksNames = new ArrayList<String>();

        for (Map.Entry<String,TaskState> entry: this.entrySet()) {
            if (entry.getValue()!= TaskState.DELETED) {
                activeTasksNames.add(entry.getKey());
            }
        }
        return activeTasksNames;
    }

    public TaskState ACTION_USERTASK(String task, UserAction action) {
        TaskState checkState;
        if (this.containsKey(task)) {
            if (action.getState() == this.get(task).getNext() | (action.getState() == this.get(task).getPrev())) {
                checkState = action.getState();
                this.replace(task,checkState);
            } else{
                checkState = errorState;
            }
        } else {
            checkState = errorState;
        }

        return checkState;
    }
}
