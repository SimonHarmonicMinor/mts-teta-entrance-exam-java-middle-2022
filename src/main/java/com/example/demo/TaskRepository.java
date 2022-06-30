package com.example.demo;

import java.util.ArrayList;
import java.util.Objects;

public class TaskRepository {
    
    private ArrayList<Task> tasks;

    public TaskRepository() {
        tasks = new ArrayList<>();
    }

    public Task findTaskByName(String name){
        
        for (Task task : tasks) {
            if(Objects.equals(task.getTaskName(),name)){

                if (task.getStatus() == TaskStatus.DELETED){
                    return null;
                }
                
                return task;
            }
        }
        return null;
    }

    public ArrayList<Task> findByOwner(String owner){
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            if(Objects.equals(task.getOwnerName(),owner)){

                if (task.getStatus() == TaskStatus.DELETED){
                    continue;
                }

                result.add(task); 
            }
        }
        return result;
    }

    public void save(Task task){
        tasks.add(task);
    }

    public void clear(){
        tasks.clear();
    }

    
}
