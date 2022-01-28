package com.costa.socket.server.daoservice.impl;

import com.costa.socket.server.dao.TaskDao;
import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.daoservice.UserService;
import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.TaskState;
import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;
    private final UserService userService;

    public TaskServiceImpl(TaskDao taskDao, UserService userService) {
        this.taskDao = taskDao;
        this.userService = userService;
    }

    @Override
    public Optional<Task> findByDescription(String description) {
        return taskDao.findByDescription(description);
    }

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public List<Task> findAllByUser(User user) {
        return taskDao.findAllByUser(user);
    }

    @Override
    public List<Task> findAllByUserAndTaskState(User user, TaskState taskState) {
        return taskDao.findAllByUserAndTaskState(user, taskState);
    }

    /**
     * Save works as a cascade operation for relation Many {@link Task} to One {@link User}
     */
    @Override
    public boolean save(Task task) {
        if (userService.findByName(task.getUser().getName()).isEmpty())
            userService.save(task.getUser());

        return taskDao.save(task);
    }

    @Override
    public List<Task> findAllByUserName(String userName) {
        return taskDao.findAllByUserName(userName);
    }
}
