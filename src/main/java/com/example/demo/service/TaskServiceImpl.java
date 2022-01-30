package com.example.demo.service;

import com.example.demo.helper.Answer;
import com.example.demo.domain.Task;
import com.example.demo.helper.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService{

    private ArrayList<Task> taskList = new ArrayList<>();

    @Override
    public Answer save(String taskName, String userName) {
        for (Task task: taskList) {
            if (!task.getStatus().equals(TaskStatus.DELETED) && task.getName().equals(taskName))
                return Answer.ERROR;
        }
        taskList.add(new Task(taskName, userName));
        return Answer.CREATED;
    }

    @Override
    public Answer close(String taskName, String userName) {
        for (Task task: taskList) {
            if (task.getStatus().equals(TaskStatus.CREATED) && task.getName().equals(taskName) && task.getUserName().equals(userName)){
                task.setStatus(TaskStatus.CLOSED);
                return Answer.CLOSED;
            } else if(task.getName().equals(taskName) && !task.getUserName().equals(userName) && !task.getStatus().equals(TaskStatus.DELETED))
                return Answer.ACCESS_DENIED;
        }
        return Answer.ERROR;
    }

    @Override
    public Answer delete(String taskName, String userName) {
        for (Task task: taskList) {
            if (task.getStatus().equals(TaskStatus.CLOSED) && task.getName().equals(taskName) && task.getUserName().equals(userName)){
                task.setStatus(TaskStatus.DELETED);
                return Answer.DELETED;
            } else if(task.getName().equals(taskName) && !task.getUserName().equals(userName) && !task.getStatus().equals(TaskStatus.DELETED))
                return Answer.ACCESS_DENIED;
        }
        return Answer.ERROR;
    }

    @Override
    public Answer reopen(String taskName, String userName) {
        for (Task task: taskList) {
            if (task.getStatus().equals(TaskStatus.CLOSED) && task.getName().equals(taskName) && task.getUserName().equals(userName)){
                task.setStatus(TaskStatus.CREATED);
                return Answer.REOPENED;
            } else if(task.getName().equals(taskName) && !task.getUserName().equals(userName) && !task.getStatus().equals(TaskStatus.DELETED))
                return Answer.ACCESS_DENIED;
        }
        return Answer.ERROR;
    }

    @Override
    public List<Task> selectAll(String userName) {
        return taskList.stream()
                .filter(t -> !t.getStatus().equals(TaskStatus.DELETED) && t.getUserName().equals(userName))
                .collect(Collectors.toList());
    }

    //for tests

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
