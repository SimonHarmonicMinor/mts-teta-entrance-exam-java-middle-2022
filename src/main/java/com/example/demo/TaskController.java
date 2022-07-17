package com.example.demo;

import java.security.AccessControlException;
import java.util.ArrayList;

public class TaskController {

    private String userName;
    private Command command;
    private String argVal;

    private final TaskService taskService = new TaskService();

    public String processMessage(String requestString){

        String responseString = "";

        try{
            
            parseRequestString(requestString);

            switch (command){
                case CREATE_TASK:
                    taskService.createTask(userName, argVal);
                    responseString = Result.CREATED.name();
                    break;
                case CLOSE_TASK:
                    taskService.closeTask(userName, argVal);
                    responseString = Result.CLOSED.name();
                    break;
                case DELETE_TASK:
                    taskService.deleteTask(userName, argVal);
                    responseString = Result.DELETED.name();
                    break;
                case REOPEN_TASK:
                    taskService.reopenTask(userName, argVal);
                    responseString = Result.REOPENED.name();
                    break;
                case LIST_TASK:
                    ArrayList<String> listTaskName = taskService.getListTask(userName);
                    responseString = Result.TASKS.name() + " " + listTaskName.toString();
                    break;
                case __DELETE_ALL:
                    taskService.deleteAllTasks();
                    break;
            }

        }
        catch (IllegalArgumentException e) {
            responseString = Result.WRONG_FORMAT.name();
        }
        catch (AccessControlException e) {
            responseString = Result.ACCESS_DENIED.name();
        }
        catch(Exception e){
            responseString = Result.ERROR.name();
        }
        
        return responseString;
    }

    private void parseRequestString(String requestString){
      
        String[] requestParts = requestString.split(" ");

        Command command;
        
        if (requestParts.length == 1){
            command = Command.valueOf(requestParts[0]);

            if(command == Command.__DELETE_ALL){
                this.command = command;
                return;
            }
        }

        if (requestParts.length == 3){
            command = Command.valueOf(requestParts[1]);

            if((command == Command.CREATE_TASK
                 || command == Command.CLOSE_TASK) 
                 || command == Command.DELETE_TASK 
                 || command == Command.REOPEN_TASK
                 || command == Command.LIST_TASK ){

                    this.userName = requestParts[0];
                    this.command = command;
                    this.argVal = requestParts[2];
                    return;
                 }

        }
        
       throw new IllegalArgumentException("Неверный формат запроса");
    }
}

enum Command {
    CREATE_TASK,
    CLOSE_TASK,
    DELETE_TASK,
    REOPEN_TASK,
    LIST_TASK,
    __DELETE_ALL
}

enum Result{
    CREATED,
    CLOSED,
    DELETED,
    REOPENED,
    WRONG_FORMAT,
    ACCESS_DENIED,
    ERROR,
    TASKS
}
