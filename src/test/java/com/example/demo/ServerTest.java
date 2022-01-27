package com.example.demo;

import com.example.demo.enums.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test
    void test001() {
        String response = sendMessage("MNVASINK CREATE_TASK CleanRoom");
        assertEquals(Result.CREATED.toString(), response);
    }

    @Test
    void test002() {
        String response = sendMessage("MNVASINK CREATE_TASK CleanRoom2");
        assertEquals(Result.CREATED.toString(), response);
    }

    @Test
    void test003() {
        String response = sendMessage("MNVASINK CREATE_TASK CleanRoom ARGS");
        assertEquals(Result.WRONG_FORMAT.toString(), response);
    }

    @Test
    void test004() {
        String response = sendMessage("MNVASINK LIST_TASK MNVASINK");
        assertEquals(Result.TASKS + " [CleanRoom,CleanRoom2]", response);
    }

    @Test
    void test005() {
        String response = sendMessage("KATE CREATE_TASK task");
        assertEquals(Result.CREATED.toString(), response);
    }

    @Test
    void test006() {
        String response = sendMessage("MNVASINK LIST_TASK KATE");
        assertEquals(Result.TASKS + " [task]", response);
    }

    @Test
    void test007() {
        String response = sendMessage("MNVASINK CLOSE_TASK task");
        assertEquals(Result.ACCESS_DENIED.toString(), response);
    }

    @Test
    void test008() {
        String response = sendMessage("MNVASINK CREATE_TASK task");
        assertEquals(Result.ERROR.toString(), response);
    }

    @Test
    void test009() {
        String response = sendMessage("KATE CLOSE_TASK task");
        assertEquals(Result.CLOSED.toString(), response);
    }

    @Test
    void test010() {
        String response = sendMessage("KATE DELETE_TASK task");
        assertEquals(Result.DELETED.toString(), response);
    }

    @Test
    void test011() {
        String response = sendMessage("KATE CREATE_TASK task");
        assertEquals(Result.CREATED.toString(), response);
    }

    @Test
    void test012() {
        String response = sendMessage("KATE CLOSE_TASK task");
        assertEquals(Result.CLOSED.toString(), response);
    }

    @Test
    void test013() {
        String response = sendMessage("KATE REOPEN_TASK task");
        assertEquals(Result.REOPENED.toString(), response);
    }
}