package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class CloseTaskTest extends AbstractServerTest {

    @Test
    void when_createdAndCloseByOwner_then_returnClosed() throws Exception {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
    }

    @Test
    void when_createdByOneUserAndCloseByAnotherUser_then_returnAccessDenied() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void when_closeTaskThatHasNotBeenCreated_then_returnError() throws Exception {
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_close_returnError() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_closeDeletedTask_then_returnError() throws IOException {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }
}
