package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {
    static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

    String response, message;

    @Order(1)
    @Test
    @DisplayName("Позитивный кейс. Создание->Удаление задачи.")
    void testMainCommand() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("USER");
        logger.info("Проверить работу команды CREATE_TASK. Имя задачи: TestTask1");
        response = sendMessage("USER CREATE_TASK TestTask1");
        logger.info("Server response: " + response);
        assertEquals("CREATED", response);
        logger.info("Проверить работу команды LIST_TASK. Задача в состоянии: CREATED.");
        response = sendMessage("USER LIST_TASK USER");
        logger.info("Server response: " + response);
        assertEquals("[TestTask1]", response);
        logger.info("Проверить работу команды CLOSE_TASK.");
        response = sendMessage("USER CLOSE_TASK TestTask1");
        logger.info("Server response: " + response);
        assertEquals("CLOSED", response);
        logger.info("Проверить работу команды LIST_TASK. Задача в состоянии: CLOSED.");
        response = sendMessage("USER LIST_TASK USER");
        logger.info("Server response: " + response);
        assertEquals("[TestTask1]", response);
        logger.info("Проверить работу команды REOPEN_TASK.");
        response = sendMessage("USER REOPEN_TASK TestTask1");
        logger.info("Server response: " + response);
        assertEquals("REOPENED", response);
        logger.info("Проверить работу команды CLOSE_TASK.");
        response = sendMessage("USER CLOSE_TASK TestTask1");
        logger.info("Server response: " + response);
        assertEquals("CLOSED", response);
        logger.info("Проверить работу команды DELETE_TASK.");
        response = sendMessage("USER DELETE_TASK TestTask1");
        logger.info("Server response: " + response);
        assertEquals("DELETED", response);
        logger.info("Проверить работу команды LIST_TASK. Задача в состоянии: DELETED.");
        response = sendMessage("USER LIST_TASK USER");
        logger.info("Server response: " + response);
        assertEquals("[]", response);
    }

    @Order(2)
    @Test
    @DisplayName("Позитивный кейс. Создание задачи, используя название удаленной задачи.")
    void checkTaskNameRules() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("ANDREY");
        logger.info("Выполнить создание задачи с именем: TestTask2");
        response = sendMessage("ANDREY CREATE_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("CREATED", response);
        logger.info("Выполнить удаление задачи с именем: TestTask2");
        response = sendMessage("ANDREY CLOSE_TASK TestTask2");
        response = sendMessage("ANDREY DELETE_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("DELETED", response);
        logger.info("Выполнить повторное создание задачи с именем: TestTask2");
        response = sendMessage("ANDREY CREATE_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("CREATED", response);
    }

    @Order(3)
    @Test
    @DisplayName("Негативный кейс. Создание задачи, с существующем именем.")
    void createSameTask() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("KIRILL");
        logger.info("Выполнить создание задачи с именем: TestTask3");
        response = sendMessage("KIRILL CREATE_TASK TestTask3");
        logger.info("Server response: " + response);
        assertEquals("CREATED", response);
        logger.info("Выполнить повторное создание задачи с именем: TestTask3");
        response = sendMessage("KIRILL CREATE_TASK TestTask3");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_NAME_ALREADY_EXIST", response);
    }

    @Order(4)
    @Test
    @DisplayName("Позитивный кейс. Проверка правил доступа.")
    void checkAccess() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("IGOR");
        logger.info("Произвести попытку перевода статуса задачи(TestTask2) другого пользователя(ANDREY)," +
                " в статус: DELETED");
        response = sendMessage("IGOR DELETE_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("ACCESS_DENIED", response);
        logger.info("Произвести попытку перевода статуса задачи(TestTask2) другого пользователя(ANDREY)," +
                " в статус: CLOSED");
        response = sendMessage("IGOR CLOSE_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("ACCESS_DENIED", response);
        logger.info("Произвести попытку перевода статуса задачи(TestTask2) другого пользователя(ANDREY)," +
                " в статус: REOPENED");
        response = sendMessage("IGOR REOPEN_TASK TestTask2");
        logger.info("Server response: " + response);
        assertEquals("ACCESS_DENIED", response);
    }

    @Order(5)
    @Test
    @DisplayName("Негативный кейс. Проверка правил доступа к спискам задач.")
    void checkAccessTaskList() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("IGOR");
        logger.info("Произвести попытку просмотра задач другого пользователя(ANDREY)");
        response = sendMessage("IGOR LIST_TASK ANDREY");
        logger.info("Server response: " + response);
        assertEquals("[TestTask2]", response);
    }

    @Test
    @DisplayName("Негативный кейс. Проверка правил изменения статуса задачи.")
    void checkStageRules() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("ANTON");
        logger.info("Выполнить создание задачи с именем: TestTask4");
        response = sendMessage("ANTON CREATE_TASK TestTask4");
        logger.info("Server response: " + response);
        assertEquals("CREATED", response);
        logger.info("Произвести попытку перевода задачи в статусе: CREATED, в статус: DELETED");
        response = sendMessage("ANTON DELETE_TASK TestTask4");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_CURRENT_STAGE_SHOULD_BE_CLOSED", response);
        logger.info("Перевод задачи в статус: DELETED");
        response = sendMessage("ANTON CLOSE_TASK TestTask4");
        response = sendMessage("ANTON DELETE_TASK TestTask4");
        logger.info("Произвести попытку перевода задачи в статусе: DELETED, в статус: CLOSED");
        response = sendMessage("ANTON CLOSE_TASK TestTask4");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_DONT_EXIST", response);
        logger.info("Произвести попытку перевода задачи в статусе: DELETED, в статус: REOPEN");
        response = sendMessage("ANTON REOPEN_TASK TestTask4");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_DONT_EXIST", response);
    }

    @Test
    @DisplayName("Негативный кейс. Закрытие/Повторное открытие/Удаление несуществующей задачи.")
    void checkStageRules2() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("VASYA");
        logger.info("Произвести попытку перевода несуществующей задачи в статус: CLOSED");
        response = sendMessage("VASYA CLOSE_TASK TestTask5");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_DONT_EXIST", response);
        logger.info("Произвести попытку перевода несуществующей задачи в статус: REOPENED");
        response = sendMessage("VASYA REOPEN_TASK TestTask5");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_DONT_EXIST", response);
        logger.info("Произвести попытку перевода несуществующей задачи в статус: DELETED");
        response = sendMessage("VASYA DELETE_TASK TestTask5");
        logger.info("Server response: " + response);
        assertEquals("ERROR_TASK_DONT_EXIST", response);
    }

    @Test
    @DisplayName("Негативный кейс. Проверка правил формата запроса.")
    void checkFormatRules() {
        message = getMessage();
        assertEquals("Введите имя (Формат: USERNAME):", message);
        response = sendMessage("SERGEY");
        response = sendMessage("SERGEY cREATE_tASK formatTest");
        logger.info("Server response: " + response);
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("SERGEY dELETE_tASK formatTest");
        logger.info("Server response: " + response);
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("SERGEY cLOSE_tASK formatTest");
        logger.info("Server response: " + response);
        assertEquals("WRONG_FORMAT", response);
        response = sendMessage("SERGEY rEOPEN_tASK formatTest");
        logger.info("Server response: " + response);
        assertEquals("WRONG_FORMAT", response);
    }
}