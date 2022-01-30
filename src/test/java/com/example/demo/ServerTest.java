package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void test() {
        String response = sendMessage("VASYA  CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);

        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);

        response = sendMessage("VASYA REOPEN_TASK CleanRoom");
        assertEquals("REOPENED", response);

        response = sendMessage("PETYA LIST_TASK VASYA");
        assertEquals("[Task{name='CleanRoom', state=CREATED, user=VASYA}]", response);

        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);

        response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("ERROR", response);

        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);

        response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
    }

    @Test
    void test2() {
        String response = sendMessage("request2");
        assertEquals("request2", response);
    }
}