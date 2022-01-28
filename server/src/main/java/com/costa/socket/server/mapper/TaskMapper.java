package com.costa.socket.server.mapper;

import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.UserRequest;

public class TaskMapper {
    private TaskMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Task toTask(UserRequest request) {
        Task task = new Task();
        task.setUser(request.getUser());
        task.setDescription(request.getArg());
        task.setState(request.getCommand().getCommandModel().getState());
        return task;
    }
}
