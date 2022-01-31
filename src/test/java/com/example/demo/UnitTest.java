package com.example.demo;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitTest extends AbstractUnitTest {

    @Test
    @Order(1)
    void createTaskTest3() {
        String user = "VASYA";
        String command = "CREATE_TASK";
        String taskName = "CleanRoom";
        ArgumentCaptor<String> userArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> taskNameArgument = ArgumentCaptor.forClass(String.class);

        when(createHandlerMock.handle(user, taskName)).thenReturn(new Response(Response.Result.CREATED, true));

        Response response = dispatcherMockHandlers.dispatch(user + " " + command + " " + taskName);

        verify(createHandlerMock, times(1)).handle(user, taskName);
        verify(createHandlerMock).handle(userArgument.capture(), taskNameArgument.capture());
        assertEquals(user, userArgument.getValue());
        assertEquals(taskName, taskNameArgument.getValue());

        assertTrue(response.isSuccessful());
        assertEquals(Response.Result.CREATED, response.getResult());
    }

    @Test
    @Order(2)
    void exceptionTest() {
        when(createHandlerMock.handle(anyString(), anyString())).thenThrow(new NullPointerException());
        Response response = dispatcherMockHandlers.dispatch("VASYA CREATE_TASK CleanRoom");
        assertFalse(response.isSuccessful());
        assertEquals(Response.Result.ERROR, response.getResult());
    }

    @Test
    @Order(3)
    void createTaskTest() {
        String user = "VASYA";
        String command = "CREATE_TASK";
        String taskName = "CleanRoom";

        when(validatorMock.validateTaskIsAbsent(anyString())).thenReturn(false);

        Response response = dispatcher.dispatch(user + " " + command + " " + taskName);

        verify(validatorMock, times(1)).validateTaskIsAbsent(taskName);

        assertFalse(response.isSuccessful());
        assertEquals(Response.Result.ERROR, response.getResult());
    }

    @Test
    @Order(4)
    void createTaskTest2() {
        String user = "VASYA";
        String command = "CREATE_TASK";
        String taskName = "CleanRoom";

        when(validatorMock.validateTaskIsAbsent(taskName)).thenReturn(true);
        when(userRepositoryMock.findFirstByName(user)).thenReturn(Optional.empty());
        doNothing().when(userRepositoryMock).save(isA(User.class));
        doNothing().when(tasksRepositoryMock).save(isA(Task.class));

        Response response = dispatcher.dispatch(user + " " + command + " " + taskName);

        verify(validatorMock, times(2)).validateTaskIsAbsent(taskName);
        verify(userRepositoryMock, times(1)).save(isA(User.class));
        verify(tasksRepositoryMock, times(1)).save(isA(Task.class));

        assertTrue(response.isSuccessful());
        assertEquals(Response.Result.CREATED, response.getResult());
    }
}