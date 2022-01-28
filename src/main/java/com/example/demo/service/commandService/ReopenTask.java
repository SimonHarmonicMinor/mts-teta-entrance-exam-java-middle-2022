package com.example.demo.service.commandService;

import com.example.demo.exception.DemoException;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;

@MyLittleBean
public class ReopenTask extends AbstractCommandService {

    public ReopenTask() {
        this.serviceName = "REOPEN_TASK";
    }

    @Override
    public void validatePermission(String userName, String arg) throws DemoException {
        TaskStatus taskStatus = taskRepository.readStatus(arg);
        validateUserRights(arg, taskStatus, userName);
        if (taskStatus != TaskStatus.CLOSED) {
            throw new DemoException("Переоткрывать можно только закрытые задачи", "ERROR");
        }

    }

    @Override
    public String executeCommand(String userName, String arg) {
        taskRepository.updateTask(arg, TaskStatus.CREATED);
        return "REOPENED";
    }
}
