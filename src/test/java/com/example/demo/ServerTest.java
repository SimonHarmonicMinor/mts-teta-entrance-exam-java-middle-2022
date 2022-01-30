package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void test() throws Exception  {
        String response = sendMessage("request");
        assertEquals("request", response);
    }

    @Test
    void test2() throws Exception  {
        String response = sendMessage("request2");
        assertEquals("request2", response);
    }

    @Test
    void when_calledCloseTaskWithCorrectValue_then_returnClosed() throws Exception {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
    }

    @Test
    void when_calledCloseTaskAlreadyBelongToOtherUser_then_returnAccessDenied() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void when_calledCloseTaskWithIncorrectValue_then_returnAccessDenied() throws Exception {
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_calledCreateTaskWithCorrectValue_then_returnCreated() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
    }

    @Test
    void when_calledCreateTaskWithDuplicateValue_then_returnError() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void when_calledCreateTaskAlreadyCreatedByOtherUser_then_returnAccessDenied() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void when_calledDeleteTaskAlreadyClosedByOwner_then_returnDeleted() throws Exception {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
        response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
        response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
    }
//
//    @Test
//    void when_calledCreateTaskAlreadyBelongToOtherUser_then_returnAccessDenied() throws Exception {
//
//    }
//    @Test
//    void when_calledCreateTaskAlreadyBelongToOtherUser_then_returnAccessDenied() throws Exception {
//
//    }
}