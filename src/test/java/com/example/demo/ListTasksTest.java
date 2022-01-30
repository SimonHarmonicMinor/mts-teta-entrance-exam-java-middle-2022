package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ListTasksTest extends AbstractServerTest {

    @Test
    void shouldReturnListOfOwnerTasksExceptDeleted() throws Exception {
        String response = getTasksByName("VASYA");
        assertEquals("TASKS [CleanRoom, BurnRoom, CrushRoom]", response);
    }

    @Test
    void shouldReturnListOfAnotherUserTasksExceptDeleted() throws Exception {
        String response = getTasksByName("PETYA");
        assertEquals("TASKS [CleanRoom, BurnRoom, CrushRoom]", response);
    }

    private String getTasksByName(String userName) throws IOException {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CREATE_TASK BurnRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CREATE_TASK CrushRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK CrushRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("VASYA CREATE_TASK DestroyRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK DestroyRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("VASYA DELETE_TASK DestroyRoom");
        assertEquals("DELETED", response);
        return sendMessage(userName + " LIST_TASK VASYA");
    }
}
