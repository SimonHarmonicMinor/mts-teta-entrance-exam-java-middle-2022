package com.costa.socket.server.dao;

import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.TaskState;
import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Optional<Task> findByDescription(String description);
    List<Task> findAll();
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserAndTaskState(User user, TaskState taskState);
    boolean save(Task task);
}
