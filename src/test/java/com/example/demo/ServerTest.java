package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void testAccessSomeoneTask() throws Exception {
        String response;
        response = sendMessage("Gary CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("Jack DELETE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("Jack CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }
//
    @Test
    void testListTask() {
        String response;
        response = sendMessage("Jack CREATE_TASK Task1");
        assertEquals("CREATED", response);
        response = sendMessage("Jack CREATE_TASK Task2");
        assertEquals("CREATED", response);
        response = sendMessage("Gary LIST_TASK Jack");
        assertEquals("TASKS [Task1, Task2]", response);
    }

    @Test
    void testCreateSameTask() {
        String response;
        response = sendMessage("Avocato CREATE_TASK RememberTheMilk");
        assertEquals("CREATED", response);
        response = sendMessage("Cato CREATE_TASK RememberTheMilk");
        assertEquals("ERROR", response);
    }

    @Test
    void testDeleteNotClosedTask() {
        String response;
        response = sendMessage("Avocato CREATE_TASK WriteTest");
        assertEquals("CREATED", response);
        response = sendMessage("Avocato DELETE_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void testFullFlowTask() {
        String response;
        response = sendMessage("Mooncake CREATE_TASK WashRoom");
        assertEquals("CREATED", response);
        response = sendMessage("Mooncake CLOSE_TASK WashRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("Mooncake LIST_TASK Mooncake");
        assertEquals("TASKS [WashRoom]", response);
        response = sendMessage("Mooncake REOPEN_TASK WashRoom");
        assertEquals("REOPENED", response);
        response = sendMessage("Mooncake CLOSE_TASK WashRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("Mooncake DELETE_TASK WashRoom");
        assertEquals("DELETED", response);
        response = sendMessage("Mooncake LIST_TASK Mooncake");
        assertEquals("TASKS []", response);
    }

}
