package com.example.demo;

import com.example.demo.services.task.TaskException;
import com.example.demo.services.task.components.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.services.task.TaskException.Type.DEFAULT;
import static com.example.demo.services.task.components.Command.Type.LIST_TASK;
import static com.example.demo.services.task.components.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    @DisplayName("")
    void checkTask() {
        Task task = new Task("TASK");

        assertEquals(CREATED.getTaskStatusTitle(), task.getTaskStatus().getTaskStatusTitle());
        assertEquals("TASK", task.getTaskTitle());
        assertEquals("TASK", task.getTaskTitle());
        assertTrue(task.isAvailableLifeCycle());
        assertTrue(task.checkStatus(LIST_TASK.getStatusList()));

        try {
            task.updateStatusBy(CREATED);
        } catch (TaskException exception) {
            assertEquals(DEFAULT.getMessage(), exception.getMessage());
        }

        assertEquals(CLOSED.getTaskStatusTitle(), task.updateStatusBy(CLOSED));
        assertTrue(task.checkStatus(LIST_TASK.getStatusList()));
        assertEquals(DELETED.getTaskStatusTitle(), task.updateStatusBy(DELETED));
        assertFalse(task.checkStatus(LIST_TASK.getStatusList()));
    }
}
