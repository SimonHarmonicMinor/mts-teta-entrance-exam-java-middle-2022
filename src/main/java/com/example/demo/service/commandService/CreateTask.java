package com.example.demo.service.commandService;

import static java.util.Objects.nonNull;

import com.example.demo.exception.DemoException;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;

@MyLittleBean
public class CreateTask extends AbstractCommandService{

    public CreateTask() {
        this.serviceName = "CREATE_TASK";
    }

    @Override
    public void validatePermission(String userName, String arg) throws DemoException {
        TaskStatus taskStatus = taskRepository.readStatus(arg);
        if(nonNull(taskStatus)) {
            throw new DemoException("Уже есть задача с таким именем", "ERROR");
        }

    }

    @Override
    public String executeCommand(String userName, String arg) {
        userRepository.updateUserTasks(userName, arg);
        taskRepository.createTask(arg);
        return "CREATED";
    }
}
