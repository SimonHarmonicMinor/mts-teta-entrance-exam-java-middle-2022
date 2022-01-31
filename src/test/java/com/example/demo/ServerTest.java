package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test
    void wrongFormatTest() {
        String response = sendMessage("VASYA CREATE_TASK");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void createTaskTest() {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
    }

    @Test
    void deleteTaskIfAccessDeniedTest() {
        sendMessage("VASYA CREATE_TASK CleanRoom");
        String response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void deleteTaskOkTest() {
        sendMessage("VASYA CREATE_TASK CleanRoom");
        sendMessage("VASYA CLOSE_TASK CleanRoom");
        String response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
    }

    @Test
    void closeTaskOkTest() {
        sendMessage("VASYA CREATE_TASK CleanRoom");
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
    }

    @Test
    void reOpenTaskOkTest() {
        sendMessage("VASYA CREATE_TASK CleanRoom");
        sendMessage("VASYA CLOSE_TASK CleanRoom");
        String response = sendMessage("VASYA REOPEN_TASK CleanRoom");
        assertEquals("REOPENED", response);
    }

    @Test
    void listTaskOkTest() {
        sendMessage("VASYA CREATE_TASK CleanRoom");
        sendMessage("PETYA CREATE_TASK CleanRoom2");
        String response = sendMessage("VASYA LIST_TASK PETYA");
        assertEquals("TASKS [CleanRoom2]", response);
    }
}