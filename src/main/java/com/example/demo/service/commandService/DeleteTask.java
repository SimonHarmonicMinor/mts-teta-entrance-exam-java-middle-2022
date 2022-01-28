package com.example.demo.service.commandService;

import static java.util.Objects.nonNull;

import com.example.demo.exception.DemoException;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;
import java.util.Set;

@MyLittleBean
public class DeleteTask extends AbstractCommandService{

    public DeleteTask() {
        this.serviceName = "DELETE_TASK";
    }

    @Override
    public void validatePermission(String userName, String arg) throws DemoException {
        TaskStatus taskStatus = taskRepository.readStatus(arg);
        validateUserRights(arg,taskStatus,userName);
        if (taskStatus != TaskStatus.CLOSED) {
            throw new DemoException("Вы не можете удалять открытые задачи или уже удаленные","ERROR");
        }

    }

    @Override
    public String sendCommand(String userName, String arg) {
        taskRepository.updateTask(arg, TaskStatus.DELETED);
        return "DELETED";
    }
}
