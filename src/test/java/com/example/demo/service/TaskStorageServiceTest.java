package com.example.demo.service;

import com.example.demo.model.ResultType;
import com.example.demo.model.Task;
import com.example.demo.model.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskStorageServiceTest {

    TaskStorageService taskStorageService;
    Task task;

    @BeforeEach
    void init() {
        taskStorageService = new TaskStorageService();
        task = new Task("Me");
    }

    @Test
    void test01() {
        ResultType resultType = taskStorageService.addTask(task, "Me");
        assertEquals(ResultType.CREATED, resultType);
    }

    @Test
    void test02() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.addTask(task, "Me");
        assertEquals(ResultType.ERROR, resultType);
    }

    @Test
    void test03() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.addTask(task, "You");
        assertEquals(ResultType.ERROR, resultType);
    }

    @Test
    void test04() {
        taskStorageService.addTask(task, "Me");
        Optional<Task> taskFromStorage = taskStorageService.getTask(task.getName());
        assertTrue(taskFromStorage.isPresent());
    }

    @Test
    void test05() {
        taskStorageService.addTask(task, "Me");
        Optional<Task> taskFromStorage = taskStorageService.getTask("You");
        assertFalse(taskFromStorage.isPresent());
    }

    @Test
    void test06() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.deleteTask(task, "Me");
        assertEquals(ResultType.ERROR, resultType);
    }

    @Test
    void test07() {
        task.setTaskState(TaskState.CLOSED);
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.deleteTask(task, "Me");
        assertEquals(ResultType.DELETED, resultType);
    }

    @Test
    void test08() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.deleteTask(task, "You");
        assertEquals(ResultType.ACCESS_DENIED, resultType);
    }

    @Test
    void test09() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.closeTask(task, "Me");
        assertEquals(ResultType.CLOSED, resultType);
    }

    @Test
    void test10() {
        task.setTaskState(TaskState.DELETED);
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.closeTask(task, "Me");
        assertEquals(ResultType.ERROR, resultType);
    }

    @Test
    void test11() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.closeTask(task, "You");
        assertEquals(ResultType.ACCESS_DENIED, resultType);
    }

    @Test
    void test12() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.openTask(task, "Me");
        assertEquals(ResultType.ERROR, resultType);
    }

    @Test
    void test13() {
        task.setTaskState(TaskState.CLOSED);
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.openTask(task, "Me");
        assertEquals(ResultType.REOPENED, resultType);
    }

    @Test
    void test14() {
        taskStorageService.addTask(task, "Me");
        ResultType resultType = taskStorageService.openTask(task, "You");
        assertEquals(ResultType.ACCESS_DENIED, resultType);
    }

    @Test
    void test15() {
        List<Task> listOfTasks = taskStorageService.getListOfTasksForTheUser("Me");
        assertEquals(Collections.emptyList(), listOfTasks);
    }

    @Test
    void test16() {
        taskStorageService.addTask(task, "Me");
        List<Task> listOfTasks = taskStorageService.getListOfTasksForTheUser("Me");
        assertEquals("[Me]", listOfTasks.toString());
    }
}
