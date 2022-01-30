package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ServerTest extends AbstractServerTest {
  private static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

  @Test
  void testWRONG_FORMAT() {
    String response = sendMessage("request");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT WRONG_FORMAT", response);
  }

  @Test
  void test() {
    //1. VASYA CREATE_TASK CleanRoom
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT CREATED", response);

    //2. PETYA DELETE_TASK CleanRoom
    response = sendMessage("PETYA DELETE_TASK CleanRoom");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT ACCESS_DENIED", response);

    //3. PETYA CREATE_TASK Task1
    response = sendMessage("PETYA CREATE_TASK Task1");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT CREATED", response);


    //4. PETYA CREATE_TASK Task2
    response = sendMessage("PETYA CREATE_TASK Task2");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT CREATED", response);

    //5. VASYA LIST_TASK PETYA
     response = sendMessage("VASYA LIST_TASK PETYA");
    logger.info("Client get response= "+ response);
    Assertions.assertTrue("RESULT TASKS [Task1, Task2]".equals( response) ||
                    "RESULT TASKS [Task2, Task1]".equals( response));


    //6. VASYA CREATE_TASK CleanRoom
     response = sendMessage("VASYA CREATE_TASK CleanRoom");
    logger.info("Client get response= "+ response);
    assertEquals("RESULT ERROR", response);
  }
}