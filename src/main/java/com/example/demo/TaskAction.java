package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskAction {
    private Map<String, TaskDemo> tasks = new HashMap<String, TaskDemo>();

    public Map<String, TaskDemo> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, TaskDemo> tasks) {
        this.tasks = tasks;
    }

    public String createTask(String user, String name){
        if(tasks.containsKey(name)){
            return "ERROR There is already a task with the same name";
        }else{
            tasks.put(name,new TaskDemo(name,user));
            return "CREATED";
        }
    }
    public String deletedTask(String user, String name){
        if(tasks.containsKey(name)){
            tasks.remove(name);
            return "DELETED";
        }else{
            return "ERROR Task does not exist";
        }
    }
    public String closedTask(String user, String name){
        if(tasks.containsKey(name)){
            return tasks.get(name).statusNew(user,"CLOSED");
        }else{
            return "ERROR Task does not exist";
        }
    }
    public String reOpenTask(String user, String name){
        if(tasks.containsKey(name)){
            return tasks.get(name).statusNew(user,"CREATED");
        }else{
            return "ERROR Task does not exist";
        }
    }
    public String listTaskUser(String user){
        String otvet = "";
        for (TaskDemo td:tasks.values()
             ) {
            if(td.getAvtor().equals(user)){
                otvet += "," + td.toString() ;
            }
        }
        if(otvet.length()>=1){
            otvet=otvet.substring(1);
        }
        return "[" + otvet + "]";
    }

}
