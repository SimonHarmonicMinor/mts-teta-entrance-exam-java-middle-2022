package com.example.demo.service.commandService;

import com.example.demo.exception.DemoException;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;

@MyLittleBean
public class CloseTask extends AbstractCommandService{

    public CloseTask() {
        this.serviceName = "CLOSE_TASK";
    }

    @Override
    public void validatePermission(String userName, String arg) throws DemoException {
        TaskStatus taskStatus = taskRepository.readStatus(arg);
        validateUserRights(arg,taskStatus,userName);
        if (taskStatus != TaskStatus.CREATED) {
            throw new DemoException("Вы не можете закрывать открытые задачи или уже удаленные","ERROR");
        }
    }

    @Override
    public String sendCommand(String userName, String arg) {
        taskRepository.updateTask(arg, TaskStatus.CLOSED);
        return "CLOSED";
    }
}
