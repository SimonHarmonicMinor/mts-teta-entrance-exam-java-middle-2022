package com.example.demo.handlers;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CreateHandlerTest extends AbstractDatabaseSetUp {

    private final IHandler createHandler = new CreateHandler();

    @Test
    void execute() {
        Request requestError = new Request(UNKNOWN, UNKNOWN, "task1");
        Request requestCreatedTask = new Request("user2", UNKNOWN, "task7");
        Request requestCreatedUser = new Request("user3", UNKNOWN, "task8");

        Response expectedResponseError = new Response(Result.ERROR);
        Response expectedResponseCreated = new Response(Result.CREATED);

        assertEquals(expectedResponseError.getResult(), createHandler.execute(requestError, spyDb).getResult());
        assertEquals(expectedResponseCreated.getResult(), createHandler.execute(requestCreatedTask, spyDb).getResult());
        assertEquals(expectedResponseCreated.getResult(), createHandler.execute(requestCreatedUser, spyDb).getResult());

        Mockito.verify(spyDb).checkUsers(requestError.getUser());
        Mockito.verify(spyDb).checkTasks(requestError.getArg());
        Mockito.verify(spyDb).addUser(requestError.getUser());

        Mockito.verify(spyDb).checkUsers(requestCreatedTask.getUser());
        Mockito.verify(spyDb).checkTasks(requestCreatedTask.getArg());
        Mockito.verify(spyDb).addTask(requestCreatedTask.getUser(), requestCreatedTask.getArg());

        Mockito.verify(spyDb).checkUsers(requestCreatedUser.getUser());
        Mockito.verify(spyDb).addUser(requestCreatedUser.getUser());
        Mockito.verify(spyDb).checkTasks(requestCreatedUser.getArg());
        Mockito.verify(spyDb).addTask(requestCreatedUser.getUser(), requestCreatedUser.getArg());
    }
}