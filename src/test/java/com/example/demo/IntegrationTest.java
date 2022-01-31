package com.example.demo;

import com.example.demo.config.TaskConfig;
import com.example.demo.logic.TaskController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationTest {

    private static TaskController controller;

    @BeforeEach
    void beforeEach() {
        controller = TaskConfig.createController();
    }

    @Test
    void createTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String expected = "CREATED";

        String response = controller.handleRequest(c1);

        assertEquals(expected, response);
    }

    @Test
    void createAndCloseTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CLOSE_TASK CleanRoom";

        controller.handleRequest(c1);
        String response = controller.handleRequest(c2);

        String expected = "CLOSED";
        assertEquals(expected, response);
    }

    @Test
    void closeAndReopenTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CLOSE_TASK CleanRoom";
        String c3 = "VASYA REOPEN_TASK CleanRoom";

        controller.handleRequest(c1);
        controller.handleRequest(c2);
        String response = controller.handleRequest(c3);

        String expected = "REOPENED";
        assertEquals(expected, response);
    }

    @Test
    void closeAndDeleteTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CLOSE_TASK CleanRoom";
        String c3 = "VASYA DELETE_TASK CleanRoom";

        controller.handleRequest(c1);
        controller.handleRequest(c2);
        String response = controller.handleRequest(c3);

        String expected = "DELETED";
        assertEquals(expected, response);
    }

    @Test
    void createAndDeleteTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c3 = "VASYA DELETE_TASK CleanRoom";

        controller.handleRequest(c1);
        String response = controller.handleRequest(c3);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void reopenAndDeleteTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CLOSE_TASK CleanRoom";
        String c3 = "VASYA REOPEN_TASK CleanRoom";
        String c4 = "VASYA DELETE_TASK CleanRoom";

        controller.handleRequest(c1);
        controller.handleRequest(c2);
        controller.handleRequest(c3);
        String response = controller.handleRequest(c4);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void reopenTaskTest() {
        String c4 = "VASYA REOPEN_TASK CleanRoom";

        String response = controller.handleRequest(c4);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void closeTaskTest() {
        String c4 = "VASYA CLOSE_TASK CleanRoom";

        String response = controller.handleRequest(c4);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void deleteTaskTest() {
        String c4 = "VASYA DELETE_TASK CleanRoom";

        String response = controller.handleRequest(c4);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void isTaskNameBusy() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "PETYA CREATE_TASK CleanRoom";

        controller.handleRequest(c1);

        String response = controller.handleRequest(c2);

        String expected = "ERROR";
        assertEquals(expected, response);
    }

    @Test
    void listTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CREATE_TASK Jogging";
        String c3 = "VASYA CREATE_TASK WashDishes";
        String c4 = "VASYA LIST_TASK VASYA";

        controller.handleRequest(c1);
        controller.handleRequest(c2);
        controller.handleRequest(c3);
        String response = controller.handleRequest(c4);

        String expected = "TASKS [CleanRoom, Jogging, WashDishes]";
        assertEquals(expected, response);
    }

    @Test
    void notAccessClosingTaskTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "PETYA CLOSE_TASK CleanRoom";


        controller.handleRequest(c1);
        String response = controller.handleRequest(c2);

        String expected = "ACCESS_DENIED";
        assertEquals(expected, response);
    }

    @Test
    void invalidCommandLengthTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom OTHER Words";

        String response = controller.handleRequest(c1);

        String expected = "WRONG_FORMAT";
        assertEquals(expected, response);
    }

    @Test
    void invalidCommandTest() {
        String c1 = "VASYA UPDATE_TASK CleanRoom";

        String response = controller.handleRequest(c1);

        String expected = "WRONG_FORMAT";
        assertEquals(expected, response);
    }

    @Test
    void createTaskWithDeletedNameTest() {
        String c1 = "VASYA CREATE_TASK CleanRoom";
        String c2 = "VASYA CLOSE_TASK CleanRoom";
        String c3 = "VASYA DELETE_TASK CleanRoom";
        String c4 = "PETYA CREATE_TASK CleanRoom";

        controller.handleRequest(c1);
        controller.handleRequest(c2);
        controller.handleRequest(c3);
        String response = controller.handleRequest(c4);

        String expected = "CREATED";
        assertEquals(expected, response);
    }
}
