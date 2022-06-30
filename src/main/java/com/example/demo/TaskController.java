package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskController {

    private String userName;
    private String command;
    private String argVal;

    private TaskService taskService;

    public TaskController(){

        taskService = new TaskService();

    }
    public String processMessage(String requestString){

        String result = "";

        try{
            
            parseRequestString(requestString);

            switch (command){
                case "CREATE_TASK":
                    taskService.createTask(userName, argVal);
                    result = "CREATED";
                    break;
                case "CLOSE_TASK":
                    taskService.closeTask(userName, argVal);
                    result = "CLOSED";
                    break;
                case "DELETE_TASK":
                    taskService.deleteTask(userName, argVal);
                    result = "DELETED";
                    break;
                case "REOPEN_TASK":
                    taskService.reopenTask(userName, argVal);
                    result = "REOPENED";
                    break;
                case "LIST_TASK":
                    ArrayList<String> listTaskName = taskService.getListTask(userName);
                    result = "TASKS " + listTaskName.toString();
                    break;
                case "__DELETE_ALL":
                    taskService.deleteAllTasks();
                    break;
            }

        }
        catch (IllegalArgumentException e) {
            result = "WRONG_FORMAT";
        }
        catch (IllegalAccessException e) {
            result = "ACCESS_DENIED";
        }
        catch(Exception e){
            result = "ERROR";
        }
        
        return result;
    }

    private void parseRequestString(String requestString){
      
        String[] requestParts = requestString.split(" ");

        
        if (requestParts.length == 1 && requestParts[0].equals("__DELETE_ALL")){
            this.command = requestParts[0];
            return;
        }

        if (requestParts.length == 2 && requestParts[0].equals("LIST_TASK")){
            this.command = requestParts[0];
            this.userName = requestParts[1];
            return;
        }

        if (requestParts.length == 3 && (requestParts[1].equals("CREATE_TASK")
            || requestParts[1].equals("CLOSE_TASK") || requestParts[1].equals("DELETE_TASK") 
            || requestParts[1].equals("REOPEN_TASK"))){

            this.userName = requestParts[0];
            this.command = requestParts[1];
            this.argVal = requestParts[2];
            return;

        }
        
       throw new IllegalArgumentException("Неверный формат запроса");

               
    }

}
