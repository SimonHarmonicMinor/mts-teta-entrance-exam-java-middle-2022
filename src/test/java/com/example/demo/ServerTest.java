package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.enums.Result;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {

    @Test
    @Order(1)
    @DisplayName("Создать задачу (CREATED)")
    void testCreated() {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals(Result.CREATED.name(), response);
    }

    @Test
    @Order(2)
    @DisplayName("Нельзя создать задачу с одинаковым именем (ERROR)")
    void testCreateWithSameName() {
        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    @Order(3)
    @DisplayName("Создать вторую задачу (CREATED)")
    void testCreatedSecond() {
        String response = sendMessage("PETYA CREATE_TASK WashDish");
        assertEquals(Result.CREATED.name(), response);
    }

    @Test
    @Order(4)
    @DisplayName("Нельзя удалить новую задачу (ERROR)")
    void testDeleteSecondWithCreated() {
        String response = sendMessage("PETYA CREATE_TASK WashDish");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    @Order(5)
    @DisplayName("Закрыть вторую задачу (CLOSED)")
    void testCloseSecond() {
        String response = sendMessage("PETYA CLOSE_TASK WashDish");
        assertEquals(Result.CLOSED.name(), response);
    }

    @Test
    @Order(6)
    @DisplayName("Заново открыть вторую задачу (REOPENED)")
    void testReopenSecond() {
        String response = sendMessage("PETYA REOPEN_TASK WashDish");
        assertEquals(Result.REOPENED.name(), response);
    }

    @Test
    @Order(7)
    @DisplayName("Закрыть вторую задачу (CLOSED)")
    void testRecloseSecond() {
        String response = sendMessage("PETYA CLOSE_TASK WashDish");
        assertEquals(Result.CLOSED.name(), response);
    }

    @Test
    @Order(8)
    @DisplayName("Удалить вторую задачу (DELETED)")
    void testDeleteSecond() {
        String response = sendMessage("PETYA DELETE_TASK WashDish");
        assertEquals(Result.DELETED.name(), response);
    }

    @Test
    @Order(9)
    @DisplayName("Нельзя заново открыть удаленную задачу (ERROR)")
    void testReopenDeleted() {
        String response = sendMessage("PETYA REOPEN_TASK WashDish");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    @Order(10)
    @DisplayName("Свой пустой список задач")
    void testSelfEmpty() {
        String response = sendMessage("VASYA LIST_TASK");
        assertEquals(Result.TASKS.name() + " []", response);
    }

    @Test
    @Order(11)
    @DisplayName("Свой непустой список задач")
    void testSelfNotEmpty() {
        String response = sendMessage("PETYA LIST_TASK");
        assertEquals(Result.TASKS.name() + " [CleanRoom]", response);
    }


    @Test
    @Order(12)
    @DisplayName("Чужой пустой список задач")
    void testOtherEmpty() {
        String response = sendMessage("PETYA LIST_TASK VASYA");
        assertEquals(Result.TASKS.name() + " []", response);
    }

    @Test
    @Order(13)
    @DisplayName("Чужой непустой список задач")
    void testOtherNotEmpty() {
        String response = sendMessage("VASYA LIST_TASK PETYA");
        assertEquals(Result.TASKS.name() + " [CleanRoom]", response);
    }
}