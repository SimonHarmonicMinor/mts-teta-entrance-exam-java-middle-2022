package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test01() {
    String response = sendMessage("request");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test02() {
    String response = sendMessage("request2 request2");
    assertEquals("WRONG_FORMAT", response);
  }


  @Test
  void test03() {
    String response = sendMessage("request2 request2 request2");
    assertEquals("WRONG_FORMAT", response);
  }
  @Test
  void test04() {
    String response = sendMessage("USER CREATE_TASK MyTask");
    assertEquals("CREATED", response);
  }
  @Test
  void test05() {
    String response = sendMessage("USER2 CREATE_TASK MyTask3");
    assertEquals("CREATED", response);
  }
  @Test
  void test06() {
    String response = sendMessage("USER CREATE_TASK MyTask2");
    assertEquals("CREATED", response);
  }
  @Test
  void test07() {
    String response = sendMessage("USER CREATE_TASK MyTask2");
    assertEquals("ERROR", response);
  }
  @Test
  void test08() {
    String response = sendMessage("USER LIST_TASK USER");
    assertEquals("TASKS [MyTask, MyTask2]", response);
  }

  @Test
  void test09() {
    String response = sendMessage("USER LIST_TASK USER3");
    assertEquals("TASKS []", response);
  }
  @Test
  void test10() {
    String response = sendMessage("USER CLOSE_TASK MyTask2");
    assertEquals("CLOSED", response);
  }
  @Test
  void test11() {
    String response = sendMessage("USER CLOSE_TASK MyTaskFiction");
    assertEquals("ERROR", response);
  }
  @Test
  void test12() {
    String response = sendMessage("USER CLOSE_TASK MyTask3");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  void test13() {
    String response = sendMessage("USER DELETE_TASK MyTask2");
    assertEquals("DELETED", response);
  }
  @Test
  void test14() {
    String response = sendMessage("USER DELETE_TASK MyTask3");
    assertEquals("ACCESS_DENIED", response);
  }
   @Test
   void test15() {
      String response = sendMessage("USER DELETE_TASK MyTaskFiction");
      assertEquals("ERROR", response);

  }
  @Test
  void test16() {
    String response = sendMessage("USER2 REOPEN_TASK MyTask2");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  void test17() {
    String response = sendMessage("USER REOPEN_TASK MyTask2");
    assertEquals("ERROR", response);
  }
  @Test
  void test18() {
    String response = sendMessage("USER REOPEN_TASK MyTaskFiction");
    assertEquals("ERROR", response);
  }
  @Test
  void test19() {
    String response = sendMessage("USER2 CREATE_TASK MyTask");
    assertEquals("ERROR", response);
  }
  @Test
  void test20() {
    String response = sendMessage("USER CREATE_TASK MyTask");
    assertEquals("ERROR", response);
  }
  @Test
    void test21() {
    String response = sendMessage("USER CREATE_TASK MyTask4");
    assertEquals("CREATED", response);
  }
  @Test
  void test22() {
    String response = sendMessage("USER CLOSE_TASK MyTask4");
    assertEquals("CLOSED", response);
  }
  @Test
  void test23() {
    String response = sendMessage("USER2 REOPEN_TASK MyTask4");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test24() {
    String response = sendMessage("USER REOPEN_TASK MyTask4");
    assertEquals("REOPEN", response);
  }

 //////////////////
  @Test
  void test25() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }


  @Test
  void test26() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test27() {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test28() {
    String response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
  }

  @Test
  void test29() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);
  }

  @Test
  void test30() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }



//  VASYA CREATE_TASK CleanRoom
//          CREATED
//  PETYA DELETE_TASK CleanRoom
//          ACCESS_DENIED
//  PETYA CREATE_TASK Task1
//          CREATED
//  PETYA CREATE_TASK Task2
//          CREATED
//  VASYA LIST_TASK PETYA
//  TASKS [Task1, Task2]
//  VASYA CREATE_TASK CleanRoom
//          ERROR


}