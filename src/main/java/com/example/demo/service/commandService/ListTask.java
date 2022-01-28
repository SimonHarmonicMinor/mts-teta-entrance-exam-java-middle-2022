package com.example.demo.service.commandService;

import com.example.demo.exception.DemoException;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;
import java.util.Set;

@MyLittleBean
public class ListTask extends AbstractCommandService{

    public ListTask() {
        this.serviceName = "LIST_TASK";
    }

    @Override
    public void validatePermission(String userName, String arg) {

    }

    @Override
    public String executeCommand(String userName, String arg) {
        Set<String> tasks = userRepository.readTasksForUser(arg);
        StringBuilder response = new StringBuilder("TASKS [");
        if (tasks.isEmpty()) {
            response.append("]");
        } else {
            tasks.stream()
                    .filter(elem -> taskRepository.readStatus(elem) != TaskStatus.DELETED)
                    .forEach(elem -> {
                        response.append(elem);
                        response.append(", ");
                    });
            response.replace(response.length() - 2, response.length(), "]");
        }

        return response.toString();
    }
}
