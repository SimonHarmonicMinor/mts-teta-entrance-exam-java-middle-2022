package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends BaseServerTest {

    @Test
    @DisplayName("")
    void test1() {
        String response = sendMessage("request CREATE_TASK vv");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    @DisplayName("")
    void test2() {
        sendMessage("request LIST_TASK ");
        String response = sendMessage("request LIST_TASK vv");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    @DisplayName("")
    void test3() {
        String response = sendMessage("request LIST_TASK ");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    @DisplayName("")
    void test4() {
        String response = sendMessage("request2");
        assertEquals("WRONG_FORMAT", response);
    }
}