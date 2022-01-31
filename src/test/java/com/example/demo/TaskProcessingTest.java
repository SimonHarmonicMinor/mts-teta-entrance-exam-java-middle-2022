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
        assertEquals("WRONG_FORMAT", new TaskProcessing("VASYA").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CREATE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("VASYA CREATE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("VASYA CREATE_TASK name arg").processingRequest());

        assertEquals("CREATED", new TaskProcessing("VASYA CREATE_TASK testCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("vASYA CREATE_TASK testCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("VASYA CREATE_TASK testcreate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("VASYA CREATE_TASK Testcreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("VASYA CREATE_TASK TestCreate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("PETYA CREATE_TASK TestCreate").processingRequest());

        assertEquals("ERROR", new TaskProcessing("VASYA CREATE_TASK TestCreate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("PETYA CREATE_TASK TestCreate").processingRequest());


        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPENED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPEN_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETE_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("PETYA CLOSED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("PETYA REOPENED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("PETYA DELETED").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSED testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPENED testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETED testUpdate").processingRequest());

        assertEquals("WRONG_FORMAT", new TaskProcessing("CLOSE_TASK testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("REOPEN_TASK testUpdate").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("DELETE_TASK testUpdate").processingRequest());

        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("CREATED", new TaskProcessing("IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("REOPENED", new TaskProcessing("IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN REOPEN_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());

        assertEquals("CREATED", new TaskProcessing("IVAN CREATE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN DELETE_TASK test").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN REOPEN_TASK test").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN CLOSE_TASK test").processingRequest());
        assertEquals("CREATED", new TaskProcessing("NINA CREATE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("NINA CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("IVAN CLOSE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("NINA CLOSE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ACCESS_DENIED", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("DELETED", new TaskProcessing("NINA DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("IVAN DELETE_TASK testUpdate").processingRequest());
        assertEquals("ERROR", new TaskProcessing("NINA DELETE_TASK testUpdate").processingRequest());


        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST_TASK").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST").processingRequest());
        assertEquals("WRONG_FORMAT", new TaskProcessing("LIST_TASK checkTaskList").processingRequest());
        assertEquals("ERROR", new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        assertEquals("ERROR", new TaskProcessing("MAKAR LIST_TASK ").processingRequest());
        assertEquals("ERROR", new TaskProcessing("MAKAR LIST_TASK DANIL").processingRequest());

        ArrayList<String> makar = new ArrayList<>();
        makar.add("checkTaskList");
        assertEquals("CREATED", new TaskProcessing("MAKAR CREATE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("MAKAR CLOSE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        assertEquals("REOPENED", new TaskProcessing("MAKAR REOPEN_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("MAKAR CLOSE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        makar.clear();
        assertEquals("DELETED", new TaskProcessing("MAKAR DELETE_TASK checkTaskList").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());
        makar.add("checkTaskList");
        makar.add("checkTaskList2");
        assertEquals("CREATED", new TaskProcessing("MAKAR CREATE_TASK checkTaskList").processingRequest());
        assertEquals("CREATED", new TaskProcessing("MAKAR CREATE_TASK checkTaskList2").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("MAKAR LIST_TASK").processingRequest());

        assertEquals("ERROR", new TaskProcessing("DANIL LIST_TASK MAKAR").processingRequest());
        ArrayList<String> danil = new ArrayList<>();
        danil.add("checkTaskList3");
        assertEquals("CREATED", new TaskProcessing("DANIL CREATE_TASK checkTaskList3").processingRequest());
        assertEquals(makar.toString(), new TaskProcessing("DANIL LIST_TASK MAKAR").processingRequest());
        assertEquals(danil.toString(), new TaskProcessing("MAKAR LIST_TASK DANIL").processingRequest());
        assertEquals("CLOSED", new TaskProcessing("DANIL CLOSE_TASK checkTaskList3").processingRequest());
        danil.clear();
        assertEquals("DELETED", new TaskProcessing("DANIL DELETE_TASK checkTaskList3").processingRequest());
        assertEquals(danil.toString(), new TaskProcessing("MAKAR LIST_TASK DANIL").processingRequest());
    }
}
