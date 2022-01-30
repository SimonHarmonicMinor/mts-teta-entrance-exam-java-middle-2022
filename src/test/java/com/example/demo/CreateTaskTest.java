package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class CreateTaskTest extends AbstractServerTest {

    @Test
    void when_calledCreateTaskWithCorrectValue_then_returnCreated() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
    }

    @Test
    void when_calledCreateTaskThatHasAlreadyBeenCreatedByOwner_then_returnError() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_calledCreateTaskAlreadyCreatedByAnotherUser_then_returnAccessDenied() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void testCreateTaskWhenDeletedByAnotherUser() throws IOException {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
    }
}
