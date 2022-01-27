package com.example.demo.services;

import com.example.demo.enums.Result;
import com.example.demo.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandServiceTest {

    private CommandService commandService;

    @BeforeEach
    void setUpDatabase(){
        commandService = new CommandService();
        commandService.execute("user1 CREATE_TASK task1");
        commandService.execute("user1 CREATE_TASK task2");
        commandService.execute("user1 CREATE_TASK task3");
        commandService.execute("user2 CREATE_TASK task4");
        commandService.execute("user2 CREATE_TASK task5");
        commandService.execute("user2 CREATE_TASK task6");
    }

    @Test
    void executeCreate() {
        String commandUnsuccessfulCreate = "user1 CREATE_TASK task1";
        String commandSuccessfulCreate = "user1 CREATE_TASK task7";

        Result expectedUnsuccessfulCreate = Result.ERROR;
        Result expectedSuccessfulCreate = Result.CREATED;

        assertEquals(expectedUnsuccessfulCreate, commandService.execute(commandUnsuccessfulCreate).getResult());
        assertEquals(expectedSuccessfulCreate, commandService.execute(commandSuccessfulCreate).getResult());
    }

    @Test
    void executeClose() {
        String commandUnsuccessfulClose = "user1 CLOSE_TASK task5";
        String commandSuccessfulClose = "user1 CLOSE_TASK task1";

        Result expectedUnsuccessfulClose = Result.ACCESS_DENIED;
        Result expectedSuccessfulClose = Result.CLOSED;

        assertEquals(expectedUnsuccessfulClose, commandService.execute(commandUnsuccessfulClose).getResult());
        assertEquals(expectedSuccessfulClose, commandService.execute(commandSuccessfulClose).getResult());
    }

    @Test
    void executeDelete() {
        commandService.execute("user1 CLOSE_TASK task1");
        String commandUnsuccessfulDelete = "user1 DELETE_TASK task2";
        String commandSuccessfulDelete = "user1 DELETE_TASK task1";

        Result expectedUnsuccessfulDelete = Result.ERROR;
        Result expectedSuccessfulDelete = Result.DELETED;

        assertEquals(expectedUnsuccessfulDelete, commandService.execute(commandUnsuccessfulDelete).getResult());
        assertEquals(expectedSuccessfulDelete, commandService.execute(commandSuccessfulDelete).getResult());
    }

    @Test
    void executeReopen() {
        commandService.execute("user1 CLOSE_TASK task1");
        String commandUnsuccessfulReopen = "user1 REOPEN_TASK task2";
        String commandSuccessfulReopen = "user1 REOPEN_TASK task1";

        Result expectedUnsuccessfulReopen = Result.ERROR;
        Result expectedSuccessfulReopen = Result.REOPENED;

        assertEquals(expectedUnsuccessfulReopen, commandService.execute(commandUnsuccessfulReopen).getResult());
        assertEquals(expectedSuccessfulReopen, commandService.execute(commandSuccessfulReopen).getResult());
    }

    @Test
    void executeList() {
        String commandSuccessfulEmptyList = "user1 LIST_TASK unknown";
        String commandSuccessfulList = "user1 LIST_TASK user2";

        Response expectedSuccessfulEmptyList = new Response(Result.TASKS);
        Response expectedSuccessfulList = new Response(Result.TASKS);
        Set<String> taskSet = new LinkedHashSet<>();
        taskSet.add("task4");
        taskSet.add("task5");
        taskSet.add("task6");
        expectedSuccessfulList.setArgs(taskSet);

        assertEquals(expectedSuccessfulEmptyList.getResult(), commandService.execute(commandSuccessfulEmptyList).getResult());
        assertEquals(expectedSuccessfulList.getResult(), commandService.execute(commandSuccessfulList).getResult());
        assertEquals(expectedSuccessfulEmptyList.getArgs(), commandService.execute(commandSuccessfulEmptyList).getArgs());
        assertEquals(expectedSuccessfulList.getArgs(), commandService.execute(commandSuccessfulList).getArgs());
    }


}