package com.example.demo.repositories;

import com.example.demo.Container;
import com.example.demo.models.Task;
import com.example.demo.models.statuses.DeletedStatus;
import com.example.demo.repositories.exceptions.TaskExistsException;
import com.example.demo.repositories.exceptions.TaskNotFoundException;

import java.util.ArrayList;

public class TaskRepository extends Repository {

    private final static String separator = "_";

    public Task getTask(String title) throws TaskNotFoundException {
        Task task = Container.getTask(title);
        if (task == null) {
            throw new TaskNotFoundException();
        }
        return task;
    }

    public void createTask(Task task) throws Exception {
        Task saved;
        try {
            saved = this.getTask(task.getTitle());
        } catch (TaskNotFoundException e) {
            saved = null;
        }
        if (saved != null && !(saved.getStatus() instanceof DeletedStatus)) {
            throw new TaskExistsException();
        }
        Container.putTaskIndex(task.getUser(), task.getTitle());
        this.setTask(task);
    }

    public ArrayList<Task> listTask(String user) {
        ArrayList<Task> out = new ArrayList<>();
        ArrayList<String> taskIndexesByUser = Container.getTaskIndexes(user);
        if (taskIndexesByUser == null) {
            return out;
        }
        for (String index : taskIndexesByUser) {
            Task task = Container.getTask(index);
            if (!(task.getStatus() instanceof DeletedStatus)) {
                out.add(task);
            }
        }
        return out;
    }

    public void setTask(Task task) throws Exception {
        String key = task.getTitle();
        Container.putTask(key, task);
    }


}
