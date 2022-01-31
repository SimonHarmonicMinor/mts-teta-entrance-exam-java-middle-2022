package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends BaseServerTest {

    @Test
    @DisplayName("Проверка БП создания задачи, включая уникальности имени")
    void checkCreateTask() {
        assertEquals("WRONG_FORMAT", sendMessage(""));
        assertEquals("WRONG_FORMAT", sendMessage("VASYA"));
        assertEquals("WRONG_FORMAT", sendMessage("CREATE_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("VASYA CREATE_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("VASYA CREATE_TASK name arg"));

        assertEquals("CREATED", sendMessage("VASYA CREATE_TASK testCreate"));
        assertEquals("CREATED", sendMessage("vASYA CREATE_TASK testCreate"));
        assertEquals("CREATED", sendMessage("VASYA CREATE_TASK testcreate"));

        assertEquals("CREATED", sendMessage("VASYA CREATE_TASK Testcreate"));
        assertEquals("CREATED", sendMessage("VASYA CREATE_TASK TestCreate"));
        assertEquals("CREATED", sendMessage("PETYA CREATE_TASK TestCreate"));

        assertEquals("ERROR", sendMessage("VASYA CREATE_TASK TestCreate"));
        assertEquals("ERROR", sendMessage("PETYA CREATE_TASK TestCreate"));
    }

    @Test
    @DisplayName("Проверка БП изменения статуса задачи, включая закрытие")
    void checkUpdateTask() {
        assertEquals("WRONG_FORMAT", sendMessage("CLOSED"));
        assertEquals("WRONG_FORMAT", sendMessage("CLOSE_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("REOPENED"));
        assertEquals("WRONG_FORMAT", sendMessage("REOPEN_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("DELETED"));
        assertEquals("WRONG_FORMAT", sendMessage("DELETE_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("PETYA CLOSED"));
        assertEquals("WRONG_FORMAT", sendMessage("PETYA REOPENED"));
        assertEquals("WRONG_FORMAT", sendMessage("PETYA DELETED"));
        assertEquals("WRONG_FORMAT", sendMessage("CLOSED testUpdate"));
        assertEquals("WRONG_FORMAT", sendMessage("REOPENED testUpdate"));
        assertEquals("WRONG_FORMAT", sendMessage("DELETED testUpdate"));

        assertEquals("WRONG_FORMAT", sendMessage("CLOSE_TASK testUpdate"));
        assertEquals("WRONG_FORMAT", sendMessage("REOPEN_TASK testUpdate"));
        assertEquals("WRONG_FORMAT", sendMessage("DELETE_TASK testUpdate"));

        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN REOPEN_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN DELETE_TASK testUpdate"));

        assertEquals("CREATED", sendMessage("IVAN CREATE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN REOPEN_TASK testUpdate"));
        assertEquals("CLOSED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN CREATE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("DELETED", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN REOPEN_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("CREATED", sendMessage("IVAN CREATE_TASK testUpdate"));
        assertEquals("CLOSED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("REOPENED", sendMessage("IVAN REOPEN_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("CLOSED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("DELETED", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN REOPEN_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK testUpdate"));

        assertEquals("CREATED", sendMessage("IVAN CREATE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN DELETE_TASK test"));
        assertEquals("ERROR", sendMessage("IVAN REOPEN_TASK test"));
        assertEquals("ERROR", sendMessage("IVAN CLOSE_TASK test"));
        assertEquals("CREATED", sendMessage("NINA CREATE_TASK testUpdate"));
        assertEquals("CLOSED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("ACCESS_DENIED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("CLOSED", sendMessage("NINA CLOSE_TASK testUpdate"));
        assertEquals("ACCESS_DENIED", sendMessage("IVAN CLOSE_TASK testUpdate"));
        assertEquals("ACCESS_DENIED", sendMessage("NINA CLOSE_TASK testUpdate"));
        assertEquals("DELETED", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("ACCESS_DENIED", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("DELETED", sendMessage("NINA DELETE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("IVAN DELETE_TASK testUpdate"));
        assertEquals("ERROR", sendMessage("NINA DELETE_TASK testUpdate"));
    }

    @Test
    @DisplayName("Проверка БП получения списка своих задач, а так же задач другого пользователя")
    void checkTaskList() {
        assertEquals("WRONG_FORMAT", sendMessage("LIST_TASK"));
        assertEquals("WRONG_FORMAT", sendMessage("LIST"));
        assertEquals("WRONG_FORMAT", sendMessage("LIST_TASK checkTaskList"));
        assertEquals("ERROR", sendMessage("MAKAR LIST_TASK"));
        assertEquals("ERROR", sendMessage("MAKAR LIST_TASK "));
        assertEquals("ERROR", sendMessage("MAKAR LIST_TASK DANIL"));

        ArrayList<String> makar = new ArrayList<>();
        makar.add("checkTaskList");
        assertEquals("CREATED", sendMessage("MAKAR CREATE_TASK checkTaskList"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));
        assertEquals("CLOSED", sendMessage("MAKAR CLOSE_TASK checkTaskList"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));
        assertEquals("REOPENED", sendMessage("MAKAR REOPEN_TASK checkTaskList"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));
        assertEquals("CLOSED", sendMessage("MAKAR CLOSE_TASK checkTaskList"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));
        makar.clear();
        assertEquals("DELETED", sendMessage("MAKAR DELETE_TASK checkTaskList"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));
        makar.add("checkTaskList");
        makar.add("checkTaskList2");
        assertEquals("CREATED", sendMessage("MAKAR CREATE_TASK checkTaskList"));
        assertEquals("CREATED", sendMessage("MAKAR CREATE_TASK checkTaskList2"));
        assertEquals(makar.toString(), sendMessage("MAKAR LIST_TASK"));

        assertEquals("ERROR", sendMessage("DANIL LIST_TASK MAKAR"));
        ArrayList<String> danil = new ArrayList<>();
        danil.add("checkTaskList3");
        assertEquals("CREATED", sendMessage("DANIL CREATE_TASK checkTaskList3"));
        assertEquals(makar.toString(), sendMessage("DANIL LIST_TASK MAKAR"));
        assertEquals(danil.toString(), sendMessage("MAKAR LIST_TASK DANIL"));
        assertEquals("CLOSED", sendMessage("DANIL CLOSE_TASK checkTaskList3"));
        danil.clear();
        assertEquals("DELETED", sendMessage("DANIL DELETE_TASK checkTaskList3"));
        assertEquals(danil.toString(), sendMessage("MAKAR LIST_TASK DANIL"));
    }

}