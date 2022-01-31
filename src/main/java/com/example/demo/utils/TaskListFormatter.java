package com.example.demo.utils;

import com.example.demo.entity.Task;

import java.util.Arrays;
import java.util.Collection;

import static com.example.demo.enums.TaskStatus.DELETED;

public class TaskListFormatter {

    private TaskListFormatter() {}

    public static String formatTaskListResponse(Collection<Task> taskList) {
        return Arrays.toString(taskList.stream()
                .filter(task -> DELETED != task.getTaskStatus())
                .map(Task::getTaskName)
                .toArray());
    }
}
