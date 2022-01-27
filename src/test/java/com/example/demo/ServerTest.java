package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

        @Test
    void test100() {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("CREATED", response);
    }

    @Test
    void test102() {
        String response = sendMessage("request2");
        assertEquals("WRONG_FORMAT", response);
    }


  @Test
  void test103() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }


    @Test
    void test104() {
        String response = sendMessage("PETYA CREATE_TASK Task1");
        assertEquals("CREATED", response);
    }

    @Test
    void test105() {
        String response = sendMessage("PETYA CREATE_TASK Task2");
        assertEquals("CREATED", response);
    }

    @Test
    void test106() {
        String response = sendMessage("VASYA LIST_TASK PETYA");
        assertEquals("TASKS [Task1, Task2]", response);
    }

    @Test
    void test107() {
        String response = sendMessage("request2");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void test108() {
        String response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }
    @Test
    void test109() {
        String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
        assertEquals("CLOSED", response);
    }
    @Test
    void test110() {
        String response = sendMessage("VASYA DELETE_TASK CleanRoom");
        assertEquals("DELETED", response);
    }

}