package com.costa.socket.server.daoservice;

import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.TaskState;
import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<Task> findByDescription(String description);
    List<Task> findAll();
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserAndTaskState(User user, TaskState taskState);
    boolean save(Task task);
    List<Task> findAllByUserName(String userName);
}
