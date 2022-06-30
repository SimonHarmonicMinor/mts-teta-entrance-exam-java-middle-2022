package com.example.demo;

import java.util.ArrayList;
import java.util.Objects;

public class TaskService {

    private ArrayList<Task> tasks;

    public TaskService() {

        tasks = new ArrayList<>();
    }

    public void createTask(String ownerName, String taskName){
        for (Task task : tasks) {
            if(Objects.equals(task.getTaskName(),taskName)){
                throw new IllegalStateException("Задача с таким именем уже существует");
            }
        }
        Task task = new Task(taskName, ownerName);
        tasks.add(task);
    }

    public void closeTask(String ownerName, String taskName) throws IllegalAccessException{
        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);
            if (Objects.equals(task.getTaskName(),taskName)){
                if(!Objects.equals(task.getOwnerName(),ownerName)){
                    throw new IllegalAccessException("Нет прав на совершение операции");
                }

                if(!Objects.equals(task.getStatus(),"CREATED")){
                    throw new IllegalStateException("Задача в неверном статусе");
                }

                task.setStatus("CLOSED");
                tasks.set(i, task);
                break;
            }
        }
    }

    public void deleteTask(String ownerName, String taskName) throws IllegalAccessException{
        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);
            if (Objects.equals(task.getTaskName(),taskName)){
                if(!Objects.equals(task.getOwnerName(),ownerName)){
                    throw new IllegalAccessException("Нет прав на совершение операции");
                }

                tasks.remove(i);
                break;
            }
        }
    }

    public void reopenTask(String ownerName, String taskName) throws IllegalAccessException{
        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            if (Objects.equals(task.getTaskName(),taskName)){
                if(!Objects.equals(task.getOwnerName(),ownerName)){
                    throw new IllegalAccessException("Нет прав на совершение операции");
                }
                if(!Objects.equals(task.getStatus(),"CLOSED")){
                    throw new IllegalStateException("Задача в неверном статусе");
                }

                task.setStatus("CREATED");
                tasks.set(i, task);
                break;
            }
        }
    }

    public ArrayList<String> getListTask(String ownerName){

        ArrayList<String> result = new ArrayList<>();
        for (Task task : tasks) {
            if (Objects.equals(task.getOwnerName(),ownerName)){
                String taskName = task.getTaskName();
                result.add(taskName);
            }
        }

        return result;
    }

    public void deleteAllTasks(){
        tasks.clear();
    }
    
}
