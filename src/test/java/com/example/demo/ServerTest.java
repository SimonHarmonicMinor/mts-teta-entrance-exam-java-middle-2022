package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {


  @Test
  void testCreate() {
    String response = "Создана задача CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    response += "Создана задачу с уже имеющимся именем CleanRoom: " + sendMessage("VASYA CREATE_TASK CleanRoom")+ "/n";
    response += "Удаляем задачу CleanRoom видим что задача не удалена: " + sendMessage("VASYA DELETE_TASK CleanRoom")+ "/n";
    response += "Меняем статус на CLOSED у задачи CleanRoom: " + sendMessage("VASYA CLOSE_TASK CleanRoom")+ "/n";
    response += "Снова удаляем задачу CleanRoom види м что она удалена: " + sendMessage("VASYA DELETE_TASK CleanRoom")+ "/n";
    response += "Создана задача CleanRoom: " + sendMessage("VASYA CREATE_TASK CleanRoom")+ "/n";
    sendMessage("VASYA DELETE_TASK CleanRoom");
    assertEquals("request", response);
  }

  @Test
  void testStatus() {
    String response = "Создана задача CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    response += "Меняем статус на CLOSED у задачи CleanRoom: " + sendMessage("VASYA CLOSE_TASK CleanRoom")+ "/n";
    response += "Меняем статус на CREATED у задачи CleanRoom: " + sendMessage("VASYA REOPEN_TASK CleanRoom")+ "/n";
    response += "Меняем статус на CLOSED у задачи CleanRoom: " + sendMessage("VASYA CLOSE_TASK CleanRoom")+ "/n";
    response += "Снова удаляем задачу CleanRoom види м что она удалена: " + sendMessage("VASYA DELETE_TASK CleanRoom")+ "/n";
    response += "Удаляем CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    assertEquals("request", response);
  }

  @Test
  void testUser() {
    String response = "Создана задача CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    response += "Создана задача CleanRoom2: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    response += "Создана задача testUser: " +  sendMessage("PETY CREATE_TASK CleanRoom") + "/n";
    response += "Получаем список задач VASYA: " +  sendMessage("VASYA LIST_TASK VASYA") + "/n";
    response += "Получаем список задач PETY: " +  sendMessage("VASYA LIST_TASK PETY") + "/n";
    response += "Закрываем задачу PETY от имени VASYA: " +  sendMessage("VASYA CLOSE_TASK testUser") + "/n";
    response += "Закрываем задачу VASYA от имени PETY: " +  sendMessage("PETY CLOSE_TASK CleanRoom2") + "/n";
    sendMessage("VASYA CLOSE_TASK CleanRoom");
    sendMessage("VASYA CLOSE_TASK CleanRoom2");
    sendMessage("PETY CLOSE_TASK testUser");
    sendMessage("VASYA DELETE_TASK CleanRoom");
    sendMessage("VASYA DELETE_TASK CleanRoom2");
    sendMessage("PETY DELETE_TASK testUser");
    assertEquals("request2", response);
  }
  @Test
  void testFormat() {
    String response = "Создана задача CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom test") + "/n";
    response += "Создана задача CleanRoom: " +  sendMessage("CREATE_TASK VASYA CleanRoom") + "/n";
    response += "Создана задача CleanRoom: " +  sendMessage("VASYA,CREATE_TASK,CleanRoom") + "/n";
    response += "Создана задача CleanRoom: " +  sendMessage("VASYA Create_task CleanRoom") + "/n";
    response += "Создана задача CleanRoom: " +  sendMessage("VASYA CREATE_TASK CleanRoom") + "/n";
    response += "Получаем список задач vasya: " +  sendMessage("VASYA LIST_TASK vasya") + "/n";
    response += "Закрываем задачу vasya: " +  sendMessage("vasya CLOSE_TASK CleanRoom") + "/n";
    sendMessage("VASYA CLOSE_TASK CleanRoom");
    sendMessage("VASYA DELETE_TASK CleanRoom");
    assertEquals("request2", response);
  }

}