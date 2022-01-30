package com.example.demo.service;

import com.example.demo.model.RequestModel;
import com.example.demo.model.ResponseModel;
import com.example.demo.model.ResultType;
import com.example.demo.model.Task;

import java.util.List;

public class RequestProcessingService {
    private static final TaskStorageService taskStorageService = new TaskStorageService();

    public ResponseModel processRequest(String request) {
        RequestModel requestModel;
        try {
            requestModel = new RequestModel(request);
        } catch (Exception e) {
            return new ResponseModel(ResultType.WRONG_FORMAT);
        }
        try {
            return executeCommand(requestModel);
        } catch (Exception e) {
            return new ResponseModel(ResultType.ERROR);
        }
    }

    private ResponseModel executeCommand(RequestModel requestModel) {
        Task task = taskStorageService.getTask(requestModel.getArgument())
                .orElse(new Task(requestModel.getArgument()));
        ResultType resultType;
        List<Task> tasks = null;
        switch (requestModel.getCommandType()) {
            case CREATE_TASK:
                resultType = taskStorageService.addTask(task, requestModel.getUserName());
                break;
            case DELETE_TASK:
                resultType = taskStorageService.deleteTask(task, requestModel.getUserName());
                break;
            case CLOSE_TASK:
                resultType = taskStorageService.closeTask(task, requestModel.getUserName());
                break;
            case REOPEN_TASK:
                resultType = taskStorageService.openTask(task, requestModel.getUserName());
                break;
            case LIST_TASK:
                tasks = taskStorageService.getListOfTasksForTheUser(requestModel.getArgument());
                resultType = ResultType.TASKS;
                break;
            default:
                return new ResponseModel(ResultType.ERROR);
        }
        return new ResponseModel(resultType, tasks);
    }
}
