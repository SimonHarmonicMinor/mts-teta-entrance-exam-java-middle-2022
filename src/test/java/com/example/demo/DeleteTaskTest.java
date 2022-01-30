package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class DeleteTaskTest extends AbstractServerTest {

    @Test
    void when_deleteClosedTask_then_returnDeleted() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
    }

    @Test
    void when_deleteClosedTaskByAnotherUser_then_returnAccessDenied() throws IOException {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void when_deleteTaskThatAlreadyHasBeenDeleted_then_returnError() throws IOException {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }
}
