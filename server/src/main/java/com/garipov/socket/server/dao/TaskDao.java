package com.garipov.socket.server.dao;

import com.garipov.socket.server.model.Task;
import com.garipov.socket.server.model.TaskState;
import com.garipov.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Optional<Task> findByDescription(String description);
    List<Task> findAll();
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserAndTaskState(User user, TaskState taskState);
    boolean save(Task task);
}
