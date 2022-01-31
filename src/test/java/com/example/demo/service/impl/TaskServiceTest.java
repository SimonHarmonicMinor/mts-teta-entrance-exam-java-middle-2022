package com.example.demo.service.impl;

import com.example.demo.exception.DemoException;
import com.example.demo.model.Command;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {

    private final TaskServiceImpl taskService = new TaskServiceImpl();

    @Test
    void testGetTaskByName_taskNotFound() {
        String taskName = "test_task";

        DemoException exception = assertThrows(DemoException.class, () -> taskService.getTaskByName(taskName));
        assertEquals("Task with name " + taskName + " does not found", exception.getMessage());
    }

    @Test
    void testGetTaskByName_success() {
        String taskName = "test_task";
        Task expected = new Task(taskName, "test_user", TaskStatus.CREATED);
        taskService.taskMap.put(taskName, expected);

        Task actual = taskService.getTaskByName(taskName);

        assertEquals(expected, actual);
    }

    @Test
    void testGetActualTasksByUserName_success() {
        Task task1 = new Task("test_task_1", "test_user_1", TaskStatus.CREATED);
        Task task2 = new Task("test_task_2", "test_user_1", TaskStatus.CLOSED);
        taskService.taskMap.put("test_task_1", task1);
        taskService.taskMap.put("test_task_2", task2);
        taskService.taskMap.put("test_task_3", new Task("test_task_3", "test_user_1", TaskStatus.DELETED));
        taskService.taskMap.put("test_task_4", new Task("test_task_4", "test_user_2", TaskStatus.CREATED));

        List<Task> actual = taskService.getActualTasksByUserName("test_user_1");

        assertEquals(List.of(task2, task1), actual);
    }

    @Test
    void testCreateTask_success() {
        taskService.taskMap.put("test_task_1", new Task("test_task_1", "test_user_1", TaskStatus.CLOSED));
        taskService.taskMap.put("test_task_2", new Task("test_task_2", "test_user_1", TaskStatus.CREATED));
        taskService.taskMap.put("test_task_3", new Task("test_task_3", "test_user_1", TaskStatus.DELETED));

        Task actual = taskService.createTask("test_user_2", "test_task_3");

        assertEquals("test_task_3", actual.getName());
        assertEquals("test_user_2", actual.getUserName());
        assertEquals(TaskStatus.CREATED, actual.getStatus());
    }

    @Test
    void testCreateTask_createNotUniqueTask() {
        taskService.taskMap.put("test_task_1", new Task("test_task_1", "test_user_1", TaskStatus.CLOSED));
        taskService.taskMap.put("test_task_2", new Task("test_task_2", "test_user_1", TaskStatus.CREATED));
        taskService.taskMap.put("test_task_3", new Task("test_task_3", "test_user_1", TaskStatus.CREATED));

        DemoException exception = assertThrows(DemoException.class, () -> taskService.createTask("test_user_2", "test_task_3"));

        assertEquals("Task names must be unique: taskName = test_task_3", exception.getMessage());
    }

    @Test
    void changeTaskStatus_doSmthWithDeletedTask() {
        String taskName = "test_task_1";
        taskService.taskMap.put(taskName, new Task(taskName, "test_user_1", TaskStatus.DELETED));

        DemoException exception = assertThrows(DemoException.class, () -> taskService.changeTaskStatus(taskName, Command.REOPEN_TASK));

        assertEquals("A task in DELETED can no longer transition to any state: taskName = " + taskName, exception.getMessage());
    }

    @Test
    void changeTaskStatus_closeTask() {
        String taskName = "test_task_1";
        taskService.taskMap.put(taskName, new Task(taskName, "test_user_1", TaskStatus.CREATED));

        TaskStatus actual = taskService.changeTaskStatus(taskName, Command.CLOSE_TASK);

        assertEquals(TaskStatus.CLOSED, actual);
    }

    @Test
    void changeTaskStatus_reopenTask() {
        String taskName = "test_task_1";
        taskService.taskMap.put(taskName, new Task(taskName, "test_user_1", TaskStatus.CLOSED));

        TaskStatus actual = taskService.changeTaskStatus(taskName, Command.REOPEN_TASK);

        assertEquals(TaskStatus.CREATED, actual);
    }

    @Test
    void changeTaskStatus_deleteTask() {
        String taskName = "test_task_1";
        taskService.taskMap.put(taskName, new Task(taskName, "test_user_1", TaskStatus.CLOSED));

        TaskStatus actual = taskService.changeTaskStatus(taskName, Command.DELETE_TASK);

        assertEquals(TaskStatus.DELETED, actual);
    }

    @Test
    void changeTaskStatus_deleteCreatedTask() {
        String taskName = "test_task_1";
        taskService.taskMap.put(taskName, new Task(taskName, "test_user_1", TaskStatus.CREATED));

        DemoException exception = assertThrows(DemoException.class, () -> taskService.changeTaskStatus(taskName, Command.DELETE_TASK));

        assertEquals("Task in the CREATED status cannot immediately change to DELETED: taskName = " + taskName, exception.getMessage());
    }

}
