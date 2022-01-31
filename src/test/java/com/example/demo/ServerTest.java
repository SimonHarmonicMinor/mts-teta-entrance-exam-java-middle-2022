package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  @DisplayName("Create task test")
  @Order(1)
  void test() {
    String petya_create_task_cleanPetyaRoom = sendMessage("PETYA CREATE_TASK CleanPetyaRoom");
    Assertions.assertEquals("CREATED", petya_create_task_cleanPetyaRoom);
  }

  @Test
  @DisplayName("Create task test")
  @Order(2)
  void test2() {
    String petya_create_task_cleanPetyaRoom = sendMessage("PETYA CREATE_TASK CleanPetyaRoomN2");
    Assertions.assertEquals("CREATED", petya_create_task_cleanPetyaRoom);
  }

  @Test
  @DisplayName("Close task test")
  @Order(3)
  void test3() {
    String petya_close_task_cleanPetyaRoom = sendMessage("PETYA CLOSE_TASK CleanPetyaRoom");
    Assertions.assertEquals("CLOSED", petya_close_task_cleanPetyaRoom);
  }

  @Test
  @DisplayName("Delete task test")
  @Order(4)
  void test4() {
    String petya_close_task_cleanPetyaRoom = sendMessage("PETYA DELETE_TASK CleanPetyaRoom");
    Assertions.assertEquals("DELETED", petya_close_task_cleanPetyaRoom);
  }

  @Test
  @DisplayName("List task test")
  @Order(5)
  void test5() {
    String vasya_list_task_petya = sendMessage("VASYA LIST_TASK PETYA");
    Assertions.assertEquals("TASKS [CleanPetyaRoomN2]", vasya_list_task_petya);
  }

}