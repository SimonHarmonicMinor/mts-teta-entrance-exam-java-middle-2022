package com.example.demo.domain;

import java.util.Collection;

public interface Store {
    void storeTask(String user, String name);

    boolean hasTask(String name);

    String getTaskOwner(String taskName);

    void setTaskClosed(String name);

    void setTaskDeleted(String name);

    void setTaskOpened(String name);

    boolean isTaskOpened(String name);

    Collection<String> queryUserTasks(String owner);

    boolean isTaskClosed(String name);

    boolean isTaskDeleted(String name);
}
