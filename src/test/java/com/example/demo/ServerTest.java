package com.example.demo;

import static com.example.demo.models.Result.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {
  private final String CRE = CREATED.toString();
  private final String DEL = DELETED.toString();
  private final String CLO = CLOSED.toString();
  private final String REO = REOPENED.toString();
  private final String WRO = WRONG_FORMAT.toString();
  private final String ACC = ACCESS_DENIED.toString();
  private final String ERR = ERROR.toString();

  @Test
  void test() {
    String response = sendMessage("User CREATE_TASK Name");
    assertEquals(CRE, response);
  }

  @Test
  void test2() {
    String response = sendMessage("User CLOSE_TASK Task_name");
    assertEquals(ERR, response);
  }

  @Test
  void test3() {
    String response = sendMessage("User CREATE_TASK Task_name");
    assertEquals(CRE, response);
  }

  @Test
  void test4() {
    String response = sendMessage("User CLOSE_TASK Task_name");
    assertEquals(CLO, response);
  }

  @Test
  void test5() {
    String response = sendMessage("User DELETE_TASK Task_name");
    assertEquals(DEL, response);
  }

  @Test
  void test6() {
    String response = sendMessage("User1 CLOSE_TASK Name");
    assertEquals(ACC, response);
  }

  @Test
  void test7() {
    String response = sendMessage("User1 CREATE_TASK Task_name");
    assertEquals(CRE, response);
  }

  @Test
  void test8() {
    String response = sendMessage("User1 DELETE_TASK Task_name");
    assertEquals(ERR, response);
  }

  @Test
  void test9() {
    String response = sendMessage("User1 CLOSE_TASK Task_name");
    assertEquals(CLO, response);
  }

  @Test
  void test10() {
    String response = sendMessage("User2 LIST_TASK User1");
    assertNotNull(response);
  }

  @Test
  void test11() {
    String response = sendMessage("User2 LIST_TASK");
    assertEquals(WRO, response);
  }

  @Test
  void test12() {
    String response = sendMessage("User CREATE_TASK Name1");
    assertEquals(CRE, response);
  }

  @Test
  void test13() {
    String response = sendMessage("User CLOSE_TASK Name1");
    assertEquals(CLO, response);
  }

  @Test
  void test14() {
    String response = sendMessage("User REOPEN_TASK Name1");
    assertEquals(REO, response);
  }
}