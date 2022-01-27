package com.example.demo.handlers;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteHandlerTest extends AbstractDatabaseSetUp {

    private final IHandler deleteHandler = new DeleteHandler();

    @BeforeEach
    void closeTasks(){
        spyDb.closeTask("user1", "task1");
    }

    @Test
    void execute() {
        Request requestError = new Request("user1", UNKNOWN, "task2");
        Request requestAccessDenied = new Request(UNKNOWN, UNKNOWN, "task1");
        Request requestDeleted = new Request("user1", UNKNOWN, "task1");

        Response expectedResponseError = new Response(Result.ERROR);
        Response expectedResponseAccessDenied = new Response(Result.ACCESS_DENIED);
        Response expectedResponseDeleted = new Response(Result.DELETED);

        assertEquals(expectedResponseError.getResult(), deleteHandler.execute(requestError, spyDb).getResult());
        assertEquals(expectedResponseAccessDenied.getResult(), deleteHandler.execute(requestAccessDenied, spyDb).getResult());
        assertEquals(expectedResponseDeleted.getResult(), deleteHandler.execute(requestDeleted, spyDb).getResult());

        Mockito.verify(spyDb, Mockito.times(2)).getTasks(requestError.getUser());
        Mockito.verify(spyDb).getTasks(requestAccessDenied.getUser());
        Mockito.verify(spyDb).deleteTask(requestDeleted.getUser(), requestDeleted.getArg());
    }
}