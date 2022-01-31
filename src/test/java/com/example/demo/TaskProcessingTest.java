package com.example.demo;

import com.example.demo.services.task.TaskProcessing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskProcessingTest {

    @Test
    @DisplayName("Проверка функционала обработки запроса(sendMessage)")
    void checkTaskProcessing() {
        assertEquals("WRONG_FORMAT", new TaskProcessing("").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_VASYA").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CREATE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_VASYA CREATE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_VASYA CREATE_TASK name arg").processingRequest());

        assertEquals("CREATED", new TaskProcessing("P_VASYA CREATE_TASK testCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_vASYA CREATE_TASK testCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_VASYA CREATE_TASK testcreate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("P_VASYA CREATE_TASK Testcreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_VASYA CREATE_TASK TestCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_PETYA CREATE_TASK TestCreate").processingRequest());

        assertEquals("ERROR", new TaskProcessing("P_VASYA CREATE_TASK TestCreate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_PETYA CREATE_TASK TestCreate").processingRequest());


        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPENED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPEN_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_PETYA CLOSED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_PETYA REOPENED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("P_PETYA DELETED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSED testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPENED testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETED testUpdate").processingRequest());

        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSE_TASK testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPEN_TASK testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETE_TASK testUpdate").processingRequest());

        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("P_IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("REOPENED", new TaskProcessing("P_IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("P_IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN DELETE_TASK test").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN REOPEN_TASK test").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN CLOSE_TASK test").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_NINA CREATE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_NINA CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("P_IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("P_NINA CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("P_NINA DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_NINA DELETE_TASK testUpdate").processingRequest());


        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST_TASK checkTaskList").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_MAKAR LIST_TASK ").processingRequest());
        assertEquals("ERROR", new TaskProcessing("P_MAKAR LIST_TASK P_DANIL").processingRequest());

        ArrayList<String> makar = new ArrayList<>();
        makar.add("checkTaskList");
        assertEquals("CREATED", new TaskProcessing("P_MAKAR CREATE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_MAKAR CLOSE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        assertEquals("REOPENED", new TaskProcessing("P_MAKAR REOPEN_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_MAKAR CLOSE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        makar.clear();
        assertEquals("DELETED", new TaskProcessing("P_MAKAR DELETE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());
        makar.add("checkTaskList");
        makar.add("checkTaskList2");
        assertEquals("CREATED", new TaskProcessing("P_MAKAR CREATE_TASK checkTaskList").processingRequest());
        assertEquals("CREATED", new TaskProcessing("P_MAKAR CREATE_TASK checkTaskList2").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_MAKAR LIST_TASK").processingRequest());

        assertEquals("ERROR", new TaskProcessing("P_DANIL LIST_TASK P_MAKAR").processingRequest());
        ArrayList<String> danil = new ArrayList<>();
        danil.add("checkTaskList3");
        assertEquals("CREATED", new TaskProcessing("P_DANIL CREATE_TASK checkTaskList3").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("P_DANIL LIST_TASK P_MAKAR").processingRequest());
        assertEquals(danil.toString(), new TaskProcessing("P_MAKAR LIST_TASK P_DANIL").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("P_DANIL CLOSE_TASK checkTaskList3").processingRequest());
        danil.clear();
        assertEquals("DELETED", new TaskProcessing("P_DANIL DELETE_TASK checkTaskList3").processingRequest());
        assertEquals(danil.toString(), new TaskProcessing("P_MAKAR LIST_TASK P_DANIL").processingRequest());
    }
}
