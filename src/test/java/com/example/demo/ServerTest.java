package com.example.demo;

import com.example.modules.AbstractServerTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test()
    void testArgs() {
        String response = sendMessage("VASYA");
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("VASYA CREATE_TASK");
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("VASYA CREATED_TASK CleanRoom 55");
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoom 123 654");
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomARG 55");
        assertEquals("CREATED", response);
    }


    @Test
    void testCreate() {
        String response = sendMessage("VASYA CREATE_TASK CleanRoomCreate");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CREATE_TASK CleanRoomCreate");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomCreate");
        assertEquals("ERROR", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomCreate1");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomCreate2");
        assertEquals("CREATED", response);
    }

    @Test
    void testDelete() {
        String response = sendMessage("VASYA DELETE_TASK CleanRoomDelete");
        assertEquals("ERROR", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomDelete");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoomDelete");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA DELETE_TASK CleanRoomDelete");
        assertEquals("DELETED", response);
        response = sendMessage("VASYA DELETE_TASK CleanRoomDelete");
        assertEquals("ERROR", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoomDelete");
        assertEquals("ERROR", response);
    }

    @Test
    void testClose() {
        String response = sendMessage("VASYA CLOSE_TASK CleanRoomClose");
        assertEquals("ERROR", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomClose");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoomClose");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoomClose");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoomClose");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoomClose");
        assertEquals("ERROR", response);
    }

    @Test
    void testOpen() {
        String response = sendMessage("VASYA REOPEN_TASK CleanRoomOpen");
        assertEquals("ERROR", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoomOpen");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA REOPEN_TASK CleanRoomOpen");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoomOpen");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA REOPEN_TASK CleanRoomOpen");
        assertEquals("ACCESS_DENIED", response);
        response = sendMessage("VASYA REOPEN_TASK CleanRoomOpen");
        assertEquals("REOPENED", response);
    }

    @Test
    void testList() {
        String response = sendMessage("ALEX LIST_TASK BOB");
        assertEquals("ERROR", response);
        response = sendMessage("ALEX CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("BOB LIST_TASK ALEX");
        assertEquals("TASKS [CleanRoom]", response);
        response = sendMessage("ALEX LIST_TASK ALEX");
        assertEquals("TASKS [CleanRoom]", response);
        response = sendMessage("ALEX CREATE_TASK CleanRoom1");
        assertEquals("CREATED", response);
        response = sendMessage("BOB LIST_TASK ALEX");
        assertEquals("TASKS [CleanRoom, CleanRoom1]", response);
        response = sendMessage("ALEX LIST_TASK ALEX");
        assertEquals("TASKS [CleanRoom, CleanRoom1]", response);
    }
}