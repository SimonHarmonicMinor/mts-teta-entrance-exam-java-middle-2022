package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.config.ConfigApp;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test3() {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test4() {
    String response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
  }

  @Test
  void test5() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);
  }

  @Test
  void test6() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }

    @Test
    void test7() {
        ConfigApp.initTaskDataBase(List.of(new Task("CleanRoom", Status.CREATED, "VASYA")));
    String response1 = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response1);
        ConfigApp.initUserDataBase(List.of(new User("VASYA", List.of("CleanRoom"))));
        String response2 = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response2);
    }
}