package com.example.demo;

import com.example.demo.model.Request;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.TasksRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.Dispatcher;
import com.example.demo.service.DispatcherImpl;
import com.example.demo.service.Validator;
import com.example.demo.service.ValidatorImpl;
import com.example.demo.service.handler.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

public class AbstractUnitTest {
    protected static UserRepository userRepositoryMock;
    protected static TasksRepository tasksRepositoryMock;
    protected static Validator validatorMock;

    protected static CreateHandler createHandler;
    protected static CloseHandler closeHandler;
    protected static ReopenHandler reopenHandler;
    protected static DeleteHandler deleteHandler;
    protected static ListHandler listHandler;

    protected static CreateHandler createHandlerMock;
    protected static CloseHandler closeHandlerMock;
    protected static ReopenHandler reopenHandlerMock;
    protected static DeleteHandler deleteHandlerMock;
    protected static ListHandler listHandlerMock;

    protected static Dispatcher dispatcherMockHandlers;
    protected static Dispatcher dispatcher;

    @BeforeAll
    static void beforeAll() {
        userRepositoryMock = Mockito.mock(UserRepositoryImpl.class);
        tasksRepositoryMock = Mockito.mock(TasksRepositoryImpl.class);
        validatorMock = mock(ValidatorImpl.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock));

        createHandlerMock = mock(CreateHandler.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock, validatorMock));
        closeHandlerMock =  mock(CloseHandler.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock, validatorMock));
        reopenHandlerMock =  mock(ReopenHandler.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock, validatorMock));
        deleteHandlerMock =  mock(DeleteHandler.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock, validatorMock));
        listHandlerMock =  mock(ListHandler.class, withSettings().useConstructor(userRepositoryMock, tasksRepositoryMock, validatorMock));

        when(createHandlerMock.getCommand()).thenReturn(Request.Command.CREATE_TASK);
        when(closeHandlerMock.getCommand()).thenReturn(Request.Command.CLOSE_TASK);
        when(reopenHandlerMock.getCommand()).thenReturn(Request.Command.REOPEN_TASK);
        when(deleteHandlerMock.getCommand()).thenReturn(Request.Command.DELETE_TASK);
        when(listHandlerMock.getCommand()).thenReturn(Request.Command.LIST_TASK);

        createHandler = new CreateHandler(userRepositoryMock, tasksRepositoryMock, validatorMock);
        closeHandler = new CloseHandler(userRepositoryMock, tasksRepositoryMock, validatorMock);
        reopenHandler = new ReopenHandler(userRepositoryMock, tasksRepositoryMock, validatorMock);
        deleteHandler = new DeleteHandler(userRepositoryMock, tasksRepositoryMock, validatorMock);
        listHandler = new ListHandler(userRepositoryMock, tasksRepositoryMock, validatorMock);

        List<Handler> mockHandlers = List.of(createHandlerMock, closeHandlerMock, reopenHandlerMock, deleteHandlerMock, listHandlerMock);
        List<Handler> handlers = List.of(createHandler, closeHandler, reopenHandler, deleteHandler, listHandler);

        dispatcherMockHandlers = new DispatcherImpl(mockHandlers);
        dispatcher = new DispatcherImpl(handlers);
    }


    @AfterAll
    static void afterAll() throws Exception {
    }
}
