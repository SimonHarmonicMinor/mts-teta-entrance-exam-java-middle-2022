package com.example.demo;

import com.example.demo.model.Response;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.types.ResponseType;
import com.example.demo.model.types.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test
    void testWrongFormatRequest() {
        String response = sendMessage("Request");
        assertEquals(new Response(ResponseType.WRONG_FORMAT).toString(), response);
    }

    @Test
    void testWrongFormatTooBigRequest() {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom room");
        assertEquals(new Response(ResponseType.WRONG_FORMAT).toString(), response);
    }

    @Test
    void testWrongFormatIllegalNameRequest() {
        String response = sendMessage("Petya CREATE_TASK CleanRoom room");
        assertEquals(new Response(ResponseType.WRONG_FORMAT).toString(), response);
    }

    @Test
    void testWrongFormatIllegalCommandRequest() {
        String response = sendMessage("PETYA create_TASK CleanRoom");
        assertEquals(new Response(ResponseType.WRONG_FORMAT).toString(), response);
    }

    @Test
    void testCreateTaskRequest() {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.CREATED).toString(), response);
    }

    @Test
    void testCreateTaskRequestWhenAlreadyExist() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CREATED));
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ERROR).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testCloseTaskRequest() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CREATED));
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.CLOSED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testDeleteTaskRequest() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CLOSED));
        String response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.DELETED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testReopenTaskRequest() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CLOSED));
        String response = sendMessage("VASYA REOPEN_TASK CleanRoom");
        assertEquals(new Response(ResponseType.REOPENED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testListTaskRequest() {
        List<String> tasks = new ArrayList<>();
        tasks.add("CleanRoom1");
        tasks.add("CleanRoom2");
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom1", TaskStatus.CLOSED));
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom2", TaskStatus.CREATED));
        String response = sendMessage("VASYA LIST_TASK VASYA");
        assertEquals(new Response(ResponseType.TASKS, tasks).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testEmptyListTaskRequest() {
        List<String> tasks = new ArrayList<>();
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom1", TaskStatus.CLOSED));
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom2", TaskStatus.CREATED));
        String response = sendMessage("VASYA LIST_TASK PETYA");
        assertEquals(new Response(ResponseType.TASKS, tasks).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testCloseTaskRequestAccessDenied() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CREATED));
        String response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ACCESS_DENIED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testDeleteTaskRequestAccessDenied() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CLOSED));
        String response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ACCESS_DENIED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testReopenTaskRequestAccessDenied() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CLOSED));
        String response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ACCESS_DENIED).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testDeleteTaskRequestWhenTaskCreated() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.CREATED));
        String response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ERROR).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testReopenTaskRequestWhenTaskDeleted() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.DELETED));
        String response = sendMessage("VASYA REOPEN_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ERROR).toString(), response);
        taskService.getTaskList().clear();
    }

    @Test
    void testCloseTaskRequestWhenTaskDeleted() {
        taskService.getTaskList().add(new Task(new User("VASYA"), "CleanRoom", TaskStatus.DELETED));
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals(new Response(ResponseType.ERROR).toString(), response);
        taskService.getTaskList().clear();
    }
}