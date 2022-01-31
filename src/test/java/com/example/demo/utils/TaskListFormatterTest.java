package com.example.demo.utils;

import com.example.demo.entity.Task;
import com.example.demo.enums.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.enums.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskListFormatterTest {

    @Test
    void formatTaskListResponseTest() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task1", CREATED));
        tasks.add(new Task("Task2", REOPENED));
        tasks.add(new Task("Task3", DELETED));

        assertEquals(TaskListFormatter.formatTaskListResponse(tasks), "[Task1, Task2]");
    }

    @Test
    void formatEmptyTaskListResponseTest() {
        List<Task> tasks = new ArrayList<>();

        assertEquals(TaskListFormatter.formatTaskListResponse(tasks), "[]");
    }
}