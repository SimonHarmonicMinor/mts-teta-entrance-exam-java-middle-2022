package com.example.demo.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDatabase implements IDatabase{
    private List<String> users;
    private Map<String, List<String>> createdTasks;
    private Map<String, List<String>> closedTasks;
    private Map<String, List<String>> deletedTasks;

    public InMemoryDatabase(){
        users = new ArrayList<>();
        createdTasks = new HashMap<>();
        closedTasks = new HashMap<>();
        deletedTasks = new HashMap<>();
    }

    @Override
    public boolean checkUsers(String userName){
        return users.contains(userName);
    }

    @Override
    public boolean addUser(String userName){
        createdTasks.put(userName, new ArrayList<>());
        closedTasks.put(userName, new ArrayList<>());
        deletedTasks.put(userName, new ArrayList<>());
        return users.add(userName);
    }

    @Override
    public boolean checkTasks(String taskName){
        boolean taskFind = false;
        for (List<String> taskList : createdTasks.values()){
            if (taskList.contains(taskName)) {
                taskFind = true;
                break;
            }
        }

        for (List<String> taskList : closedTasks.values()){
            if (taskList.contains(taskName)) {
                taskFind = true;
                break;
            }
        }

        return taskFind;
    }

    @Override
    public boolean addTask(String userName, String taskName){
        if(!createdTasks.containsKey(userName)){
            createdTasks.put(userName, new ArrayList<>());
        }

        return createdTasks.get(userName).add(taskName);
    }

    @Override
    public boolean closeTask(String userName, String taskName) {
        if(createdTasks.get(userName).remove(taskName)){
            return closedTasks.get(userName).add(taskName);
        }else{
            return false;
        }
    }

    @Override
    public boolean reopenTask(String userName, String taskName) {
        if(closedTasks.get(userName).remove(taskName)){
            return createdTasks.get(userName).add(taskName);
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteTask(String userName, String taskName) {
        if(closedTasks.get(userName).remove(taskName)){
            return deletedTasks.get(userName).add(taskName);
        }else{
            return false;
        }
    }

    @Override
    public List<String> getTasks(String userName) {

        return Stream.concat(createdTasks.get(userName).stream(),
                closedTasks.get(userName).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUsers() {
        return users;
    }

}
