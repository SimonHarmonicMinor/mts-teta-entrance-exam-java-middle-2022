package com.example.command;

import com.example.response.Response;
import com.example.task.TaskCollectionHandler;

import java.util.Arrays;

public class CommandProcessor {
    private Response response;
    private String userName;
    private String commandName;
    private String arg;
    TaskCollectionHandler taskCollectionHandler;


    public String ProcessCommand(String command){
        try{
            response = new Response();
            ParseCommand(command);
            taskCollectionHandler = new TaskCollectionHandler();
            CommandSelector();
            return response.toString();
        }
        catch (Exception e){
            if (response.getResponseCode() == null)
                response.setResponseCode(Response.ResponseCodes.ERROR);
            return response.toString();
        }
    }


   private void ParseCommand(String command) throws Exception{
       var splittedCommand = command.split("\\s+");
       if (Arrays.stream(splittedCommand).count() != 3)
       {
           response.setResponseCode(Response.ResponseCodes.WRONG_FORMAT);
           throw new Exception();
       }
       this.userName = splittedCommand[0];
       this.commandName = splittedCommand[1];
       this.arg = splittedCommand[2];
    }

    private void CommandSelector() throws Exception {
        switch (commandName){
            case ("CREATE_TASK"):
                taskCollectionHandler.CreateTask(userName, arg, response);
                break;
            case ("DELETE_TASK"):
                taskCollectionHandler.DeleteTask(userName, arg, response);
                break;
            case ("CLOSE_TASK"):
                taskCollectionHandler.CloseTask(userName, arg, response);
                break;
            case ("REOPEN_TASK"):
                taskCollectionHandler.ReopenTask(userName, arg, response);
                break;
            case ("LIST_TASK"):
                taskCollectionHandler.GetAllUserTasks(arg, response);
                break;
            default:
                response.setResponseCode(Response.ResponseCodes.ERROR);
                break;
        }

    }


}
