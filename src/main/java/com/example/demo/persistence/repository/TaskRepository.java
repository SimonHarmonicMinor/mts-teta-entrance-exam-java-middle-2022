package com.example.demo.persistence.repository;

import com.example.demo.model.Response;

public interface TaskRepository {

    void createTask(String userName, String taskName, Response response);

    void closeTask(String userName, String taskName, Response response) throws Exception;

    void deleteTask(String userName, String taskName, Response response) throws Exception;

    void reopenTask(String userName, String taskName, Response response) throws Exception;

    void getAllUserTasks(String userName, Response response);

    void deleteAllUserTasks(Response response);
}
