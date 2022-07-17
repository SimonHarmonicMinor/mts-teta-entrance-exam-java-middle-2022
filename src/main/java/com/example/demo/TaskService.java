package com.example.demo;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Objects;

public class TaskService {

    private final TaskRepository taskRepository = new TaskRepository();

    public void createTask(String ownerName, String taskName){
        if(Objects.nonNull(taskRepository.findTaskByName(taskName))){
                throw new IllegalStateException("Задача с таким именем уже существует");
        }   
        
        Task task = new Task(taskName, ownerName);
        taskRepository.save(task);
    }

    public void closeTask(String ownerName, String taskName){
        Task task = taskRepository.findTaskByName(taskName);

        if (Objects.isNull(task)){
            throw new IllegalStateException("Задача не найдена");
        }

        if(!Objects.equals(task.getOwnerName(),ownerName)){
            throw new AccessControlException("Нет прав на совершение операции");
        }

        if(!Objects.equals(task.getStatus(),TaskStatus.CREATED)){
            throw new IllegalStateException("Задача в неверном статусе");
        }

        task.setStatus(TaskStatus.CLOSED);

    }

    public void deleteTask(String ownerName, String taskName){
        Task task = taskRepository.findTaskByName(taskName);

        if (Objects.isNull(task)){
            throw new IllegalStateException("Задача не найдена");
        }

        if(!Objects.equals(task.getOwnerName(),ownerName)){
            throw new AccessControlException("Нет прав на совершение операции");
        }

        if(!Objects.equals(task.getStatus(),TaskStatus.CLOSED)){
            throw new IllegalStateException("Задача в неверном статусе");
        }

        task.setStatus(TaskStatus.DELETED);
    
    }

    public void reopenTask(String ownerName, String taskName){
        Task task = taskRepository.findTaskByName(taskName);

        if (Objects.isNull(task)){
            throw new IllegalStateException("Задача не найдена");
        }

        if(!Objects.equals(task.getOwnerName(),ownerName)){
            throw new AccessControlException("Нет прав на совершение операции");
        }

        if(!Objects.equals(task.getStatus(),TaskStatus.CLOSED)){
            throw new IllegalStateException("Задача в неверном статусе");
        }

        task.setStatus(TaskStatus.CREATED);
    }

    public ArrayList<String> getListTask(String ownerName){

        ArrayList<String> result = new ArrayList<>();
        for (Task task : taskRepository.findByOwner(ownerName)) {
                result.add(task.getTaskName());
        }

        return result;
    }

    public void deleteAllTasks(){
        taskRepository.clear();
    }
    
}
