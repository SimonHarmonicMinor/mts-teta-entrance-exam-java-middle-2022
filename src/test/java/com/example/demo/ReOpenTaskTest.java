package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ReOpenTaskTest extends AbstractServerTest {

    @Test
    void when_calledReOpenTaskByOwner_then_returnReopened() throws IOException {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals("REOPENED", response);
    }

    @Test
    void asd() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals("REOPENED", response);
        response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_calledReOpenTaskByOwnerThatHasNotBeenCreated_then_returnError() throws IOException {
        String response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

}
