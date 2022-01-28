package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {
  private final String created = "CREATED";
  private final String error = "ERROR";
  private final String closed = "CLOSED";
  private final String denied = "ACCESS_DENIED";
  private final String reopened = "REOPENED";
  private final String deleted = "DELETED";
  private final String wrongFormat = "WRONG_FORMAT";


  @Test
  @DisplayName("Тест создания записи")
  void createTest() throws Exception {
    assertEquals(created, sendMessage("VASYA CREATE_TASK CleanRoom"));
    endMessage();
    assertEquals(created, sendMessage("PETYA CREATE_TASK Task1"));
    endMessage();
    assertEquals(error, sendMessage("VASYA CREATE_TASK CleanRoom"));
    endMessage();
    assertEquals(error, sendMessage("PETYA CREATE_TASK CleanRoom"));
    endMessage();
    assertEquals(created, sendMessage("PETYA CREATE_TASK ClEanRoom"));
  }

  @Test
  @DisplayName("Тест закрытия записи")
  void testCloseCommand() throws Exception {
    sendMessage("Applejack CREATE_TASK FindApples");
    endMessage();
    sendMessage("PinkyPie CREATE_TASK FindFriends");
    endMessage();

    assertEquals(closed, sendMessage("Applejack CLOSE_TASK FindApples"), "Таска должна закрытаться в штатном режиме");
    endMessage();
    assertEquals(error, sendMessage("Applejack CLOSE_TASK FindApples"),"Нельзя закрывать закрытые таски");
    endMessage();
    assertEquals(denied, sendMessage("Applejack CLOSE_TASK FindFriends"), "Нельзя закрывать чужие таски");
    endMessage();
    sendMessage("Applejack DELETE_TASK FindApples");
    endMessage();
    assertEquals(error,sendMessage("Applejack CLOSE_TASK FindApples"), "Нельзя закрывать удаленные таски");
    endMessage();
    assertEquals(error,sendMessage("Applejack CLOSE_TASK SomeRandomText"), "Нельзя закрывать несуществующие таски");
  }

  @Test
  @DisplayName("Тест удаления записи")
  void testDeleteCommand() throws Exception {
    sendMessage("PinkyPie CREATE_TASK MakeMagic");
    endMessage();
    sendMessage("NikolayVasiljevich CREATE_TASK goToZavod");
    endMessage();
    sendMessage("PinkyPie CLOSE_TASK MakeMagic");
    endMessage();
    assertEquals(deleted, sendMessage("PinkyPie DELETE_TASK MakeMagic"), "Таска должна удалиться в штатном режиме");
    endMessage();
    assertEquals(error, sendMessage("PinkyPie DELETE_TASK MakeMagic"),"Нельзя удалять удаленные таски");
    endMessage();
    assertEquals(denied, sendMessage("PinkyPie DELETE_TASK goToZavod"), "Нельзя удалять чужие таски");
    endMessage();
    sendMessage("PinkyPie CREATE_TASK MakeBlackMagic");
    endMessage();
    assertEquals(error,sendMessage("PinkyPie DELETE_TASK MakeBlackMagic"), "Нельзя удалять открытые таски");
    endMessage();
    assertEquals(error,sendMessage("PinkyPie DELETE_TASK SomeRandomText"), "Нельзя закрывать несуществующие таски");
  }

  @Test
  @DisplayName("Тест переоткрытия")
  void testReopenCommand() throws Exception {
    sendMessage("CyrodiilChamp CREATE_TASK lootHouse");
    endMessage();
    sendMessage("randomDude CREATE_TASK Yaaaazzj");
    endMessage();
    sendMessage("CyrodiilChamp CLOSE_TASK lootHouse");
    endMessage();
    assertEquals(reopened, sendMessage("CyrodiilChamp REOPEN_TASK lootHouse"), "Таска должна открыться в штатном режиме");
    endMessage();
    assertEquals(error, sendMessage("CyrodiilChamp REOPEN_TASK lootHouse"),"Нельзя открывать открытые таски");
    endMessage();
    assertEquals(denied, sendMessage("CyrodiilChamp REOPEN_TASK Yaaaazzj"), "Нельзя открывать чужие таски");
    endMessage();
    sendMessage("CyrodiilChamp CREATE_TASK killDaedra");
    endMessage();
    assertEquals(error,sendMessage("CyrodiilChamp REOPEN_TASK killDaedra"), "Нельзя удалять открытые таски");
    endMessage();
    assertEquals(error,sendMessage("CyrodiilChamp REOPEN_TASK SomeRandomText"), "Нельзя закрывать несуществующие таски");
  }

  @Test
  @DisplayName("Тест вывода списка")
  void testList() throws Exception {
    sendMessage("Cartman CREATE_TASK MakeWorldBetter");
    endMessage();
    sendMessage("Cartman CREATE_TASK goToKFC");
    endMessage();
    sendMessage("Kenny CREATE_TASK Bastards");
    endMessage();
    assertEquals("TASKS [MakeWorldBetter, goToKFC]", sendMessage("Cartman LIST_TASK Cartman"));
    endMessage();
    assertEquals("TASKS [Bastards]", sendMessage("Cartman LIST_TASK Kenny"), "не должно быть запрета просмотра чужих записей");
    endMessage();
    sendMessage("Cartman CLOSE_TASK goToKFC");
    endMessage();
    sendMessage("Cartman DELETE_TASK goToKFC");
    endMessage();
    assertEquals("TASKS [MakeWorldBetter]", sendMessage("Cartman LIST_TASK Cartman"), "Удаленные записи не должны быть показаны");
  }

  @Test
  @DisplayName("Тест формата ввода")
  void testFormat() throws Exception{
    assertEquals(wrongFormat, sendMessage("OneWrod"));
    endMessage();
    assertEquals(wrongFormat,sendMessage("TWO WORDS"));
    endMessage();
    assertEquals(wrongFormat,sendMessage("Four very long WORDS"));
    endMessage();
    assertEquals(error, sendMessage("test wrong command"));
    endMessage();
    assertEquals(error, sendMessage("test CrEAtE_TaSk test1"), "Команды регистрозависими");
  }
}