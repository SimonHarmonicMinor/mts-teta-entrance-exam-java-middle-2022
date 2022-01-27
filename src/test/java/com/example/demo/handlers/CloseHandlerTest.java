package com.example.demo.handlers;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CloseHandlerTest extends AbstractDatabaseSetUp {

    private final IHandler closeHandler = new CloseHandler();

    @Test
    void execute() {
        Request requestAccessDenied = new Request(UNKNOWN, UNKNOWN, "task1");
        Request requestClosed = new Request("user1", UNKNOWN, "task1");

        Response expectedResponseAccessDenied = new Response(Result.ACCESS_DENIED);
        Response expectedResponseClosed = new Response(Result.CLOSED);

        assertEquals(expectedResponseAccessDenied.getResult(), closeHandler.execute(requestAccessDenied, spyDb).getResult());
        assertEquals(expectedResponseClosed.getResult(), closeHandler.execute(requestClosed, spyDb).getResult());

        Mockito.verify(spyDb).getTasks(requestAccessDenied.getUser());
        Mockito.verify(spyDb).getTasks(requestClosed.getUser());
        Mockito.verify(spyDb).closeTask(requestClosed.getUser(), requestClosed.getArg());
    }
}