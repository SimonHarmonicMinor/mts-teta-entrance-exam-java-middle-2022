package com.example.demo.services.task;

import com.example.demo.services.task.components.Command;
import com.example.demo.services.task.components.Task;
import com.example.demo.services.task.components.TaskStatus;
import com.example.demo.services.task.components.User;

import java.util.StringTokenizer;

import static com.example.demo.services.Storage.storage;
import static com.example.demo.services.task.TaskException.Type.DEFAULT;
import static com.example.demo.services.task.TaskException.Type.WRONG_FORMAT;
import static com.example.demo.services.task.components.TaskStatus.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TaskProcessing {
    private String request, response;
    private User user;
    private Command command;

    private TaskProcessing() {
    }

    public TaskProcessing(String request) {
        this.request = request;
    }

    private String getResponse() {
        if (isBlank(this.response))
            this.response = DEFAULT.getMessage();

        return this.response;
    }

    private StringTokenizer tokenizerBody() throws TaskException {
        if (isBlank(this.request))
            throw new TaskException(WRONG_FORMAT);

        StringTokenizer body = new StringTokenizer(request);
        if (body.countTokens() < 2 || body.countTokens() > 3)
            throw new TaskException(WRONG_FORMAT);

        return body;
    }

    private void buildBody(StringTokenizer body) throws TaskException {
        this.user = new User(body.nextToken());
        this.command = new Command(body.nextToken(), body.hasMoreTokens() ? body.nextToken() : null);
    }

    public String processingRequest() {
        try {
            buildBody(tokenizerBody());

            switch (this.command.getType()) {
                case CREATE_TASK:
                    this.response = createTask();
                    break;
                case CLOSE_TASK:
                    this.response = updateTask(CLOSED);
                    break;
                case REOPEN_TASK:
                    this.response = updateTask(REOPENED);
                    break;
                case DELETE_TASK:
                    this.response = updateTask(DELETED);
                    break;
                case LIST_TASK:
                    this.response = listTasks();
                    break;
                default:
                    throw new TaskException();
            }
        } catch (TaskException exception) {
            this.response = exception.getMessage();
        }

        return getResponse();
    }

    private String createTask() {
        if (this.user.isNotAddedToStorage())
            storage().getAllUser().add(this.user);

        return storage().checkUser(this.user.getUserTitle()).addTask(new Task(this.command.getArg()));
    }

    private String updateTask(TaskStatus taskStatus) throws TaskException {
        return storage()
                .checkUser(this.user.getUserTitle())
                .checkTask(this.command.getArg(), this.command.getType().getStatusList())
                .updateStatusBy(taskStatus);
    }

    private String listTasks() throws TaskException {
        User user = storage().checkUser(this.user.getUserTitle());

        if (isNotBlank(this.command.getArg()))
            return storage().checkUser(this.command.getArg()).getTaskListByString();
        else
            return user.getTaskListByString();
    }

}
