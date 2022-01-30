package com.example.demo;

import com.example.demo.request.RequestObject;
import com.example.demo.request.Response;
import com.example.demo.request.ResponseBuilder;
import com.example.demo.task.TaskManagerError;
import com.example.demo.task.TaskManagerException;
import com.example.demo.task.TaskManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Транслирует запрос клиента на объекты менеджера задач.
 *
 * Create by fkshistero on 30.01.2022.
 */
public class Connector {
    private static final Logger logger = LoggerFactory.getLogger(Connector.class);

    private TaskManagerImpl userManager = new TaskManagerImpl();

    /**
     * Send request to a task manager.
     *
     * @param requestObject  RequestData.
     * @return String with response&
     */
    public String pullChange(RequestObject requestObject) {

        try {
            switch (requestObject.getCommand()) {
                case CREATE_TASK: {
                    //как создает, так и переводит в статус create
                    if (userManager.addTask(requestObject.getUser(), requestObject.getArg())) {
                        return ResponseBuilder.createResponse(Response.CREATED);
                    }
                    break;
                }
                case DELETE_TASK: {
                    if (userManager.deleteTask(requestObject.getUser(), requestObject.getArg())) {
                        return ResponseBuilder.createResponse(Response.DELETED);
                    }
                    break;
                }
                case CLOSE_TASK: {
                    if (userManager.closeTask(requestObject.getUser(), requestObject.getArg())) {
                        return ResponseBuilder.createResponse(Response.CLOSED);
                    }
                    break;
                }
                case REOPEN_TASK: {
                    if (userManager.openTask(requestObject.getUser(), requestObject.getArg())) {
                        return ResponseBuilder.createResponse(Response.REOPENED);
                    }
                    break;
                }
                case LIST_TASK: {
                    List<String> tasks = userManager.getAllNameTasksOfUser(requestObject.getArg());
                    return ResponseBuilder.createResponse(Response.TASKS, String.join(", ", tasks));

                }
            }
        }catch (TaskManagerException exception) {
            logger.error(exception.getMessage());
            if(exception.getError().equals(TaskManagerError.NO_ACCESS)) {
                return ResponseBuilder.createResponse(Response.ACCESS_DENIED);
            }
        }
        return ResponseBuilder.createResponse(Response.ERROR);
    }


}
