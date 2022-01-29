package com.example.demo.RequestHandler;

import com.example.demo.ResultHandler.Result;
import com.example.demo.ResultHandler.ResultTypes;

import com.example.demo.TasksService.TaskOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);
    private Result result;
    private Request request;
    private TaskOperation taskOperation;

    public String RequestValidator(String requestText){
        try {
            Result result = new Result();
            Request request = new Request();
            var requestList = requestText.split("\\s+");

            if (requestList.length != 3) {
                result.setResult(ResultTypes.WRONG_FORMAT);
                return result.getResult();
            }
            request.user = requestList[0];
            request.command = requestList[1];
            request.arg = requestList[2];

            taskOperation = new TaskOperation();
            switch (request.getCommand()){
                case "CREATE_TASK":
                    taskOperation.CreateTask(request.getUser(),request.getArg(),result);
                    break;
                case "CLOSE_TASK":
                    taskOperation.CloseTask(request.getUser(),request.getArg(),result);
                    break;
                case "DELETE_TASK":
                   taskOperation.DeleteTask(request.getUser(),request.getArg(),result);
                   break;
                case "REOPEN_TASK":
                    taskOperation.ReopenTask(request.getUser(),request.getArg(),result);
                    break;
                case "LIST_TASK":
                    taskOperation.ListTask(request.getUser(),request.getArg(),result);
                    break;
                default:
                    result.setResult(ResultTypes.WRONG_FORMAT);
            }
            return result.getResult();
        }
        catch (Exception e){
            LOG.error("Invalid request", e);
            return result.getResult();
        }
    }

}
