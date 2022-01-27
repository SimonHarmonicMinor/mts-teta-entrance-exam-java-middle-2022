package com.example.demo.handlers;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ReopenHandlerTest extends AbstractDatabaseSetUp {

    private final IHandler reopenHandler = new ReopenHandler();

    @BeforeEach
    void closeTasks(){
        spyDb.closeTask("user1", "task1");
    }

    @Test
    void execute() {
        Request requestError = new Request("user1", UNKNOWN, "task2");
        Request requestAccessDenied = new Request(UNKNOWN, UNKNOWN, "task1");
        Request requestReopened = new Request("user1", UNKNOWN, "task1");

        Response expectedResponseError = new Response(Result.ERROR);
        Response expectedResponseAccessDenied = new Response(Result.ACCESS_DENIED);
        Response expectedResponseReopened = new Response(Result.REOPENED);

        assertEquals(expectedResponseError.getResult(), reopenHandler.execute(requestError, spyDb).getResult());
        assertEquals(expectedResponseAccessDenied.getResult(), reopenHandler.execute(requestAccessDenied, spyDb).getResult());
        assertEquals(expectedResponseReopened.getResult(), reopenHandler.execute(requestReopened, spyDb).getResult());

        Mockito.verify(spyDb, Mockito.times(2)).getTasks(requestError.getUser());
        Mockito.verify(spyDb).getTasks(requestAccessDenied.getUser());
        Mockito.verify(spyDb).reopenTask(requestReopened.getUser(), requestReopened.getArg());
    }
}