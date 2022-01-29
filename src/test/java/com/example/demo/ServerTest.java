package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.ResultHandler.ResultTypes;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {
  
  @Test
  void test001() {
    String response = sendMessage("OMON CREATE_TASK CleanRoom");
    assertEquals(ResultTypes.CREATED.toString(), response);
  }

  @Test
  void test002() {
    String response = sendMessage("OMON CREATE_TASK CleanRoom2");
    assertEquals(ResultTypes.CREATED.toString(), response);
  }

  @Test
  void test003() {
    String response = sendMessage("OMON CREATE_TASK CleanRoom ARGS");
    assertEquals(ResultTypes.WRONG_FORMAT.toString(), response);
  }

  @Test
  void test004() {
    String response = sendMessage("OMON LIST_TASK OMON");
    assertEquals(ResultTypes.TASKS + " [CleanRoom,CleanRoom2]", response);
  }

  @Test
  void test005() {
    String response = sendMessage("VOVA CREATE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.CREATED.toString(), response);
  }

  @Test
  void test006() {
    String response = sendMessage("OMON LIST_TASK VOVA");
    assertEquals(ResultTypes.TASKS + " [СhangeTheConstitution]", response);
  }

  @Test
  void test007() {
    String response = sendMessage("OMON CLOSE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.ACCESS_DENIED.toString(), response);
  }

  @Test
  void test008() {
    String response = sendMessage("OMON CREATE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.ERROR.toString(), response);
  }

  @Test
  void test009() {
    String response = sendMessage("VOVA CLOSE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.CLOSED.toString(), response);
  }

  @Test
  void test010() {
    String response = sendMessage("VOVA DELETE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.DELETED.toString(), response);
  }

  @Test
  void test011() {
    String response = sendMessage("VOVA CREATE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.CREATED.toString(), response);
  }

  @Test
  void test012() {
    String response = sendMessage("VOVA CLOSE_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.CLOSED.toString(), response);
  }

  @Test
  void test013() {
    String response = sendMessage("VOVA REOPEN_TASK СhangeTheConstitution");
    assertEquals(ResultTypes.REOPENED.toString(), response);
  }
  @Test
  void test014() {
    String response = sendMessage("VOVA REOPEN_TASK");
    assertEquals(ResultTypes.WRONG_FORMAT.toString(), response);
  }
  @Test
  void test015() {
    String response = sendMessage("VOVA REOPEааывN_TASK ыаываыва");
    assertEquals(ResultTypes.WRONG_FORMAT.toString(), response);
  }
  @Test
  void test016() {
    String response = sendMessage("VOVA LIST_TASK DIMA");
    assertEquals(ResultTypes.TASKS + " []", response);
  }
  @Test
  void test017() {
    String response = sendMessage("DIMA REOPEN_TASK GO_SWAP");
    assertEquals(ResultTypes.ERROR.toString(), response);
  }
}