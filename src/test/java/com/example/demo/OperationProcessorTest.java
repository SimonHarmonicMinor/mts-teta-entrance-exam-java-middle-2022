package com.example.demo;

import com.example.demo.proto.Operation;
import com.example.demo.proto.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationProcessorTest {
    private static OperationProcessor processor;
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        processor = new OperationProcessor(new InMemoryStore());
    }

    protected String sendMessage(String msg) {
        LOG.debug("client sends message: " + msg);
        try {
            return processor.processLine(msg);
        } catch (RuntimeException e) {
            LOG.debug("message sending exception: " + e.getMessage());
            return "EXCEPTION";
        }
    }

    @Test
    void testWrongFormat() {
        assertEquals(Response.WRONG_FORMAT.name(), sendMessage(""));
        assertEquals(Response.WRONG_FORMAT.name(), sendMessage("a"));
        assertEquals(Response.WRONG_FORMAT.name(), sendMessage("a b"));
        assertEquals(Response.WRONG_FORMAT.name(), sendMessage("a b c d"));
    }

    /**
     * Пользователь может добавлять, удалять, закрывать и заново открывать задачи
     */
    @Test
    void testCrud() {
        assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1, Response.CREATED);
        assertTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1, Response.CLOSED);
        assertTaskOperation(Operation.REOPEN_TASK, User.VASYA, Task.Task1, Response.REOPENED);
        assertTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1, Response.CLOSED);
        assertTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task1, Response.DELETED);
    }

    /**
     * Названия задач должны быть уникальными для всех пользователей (удаленные не учитываются).
     */
    @Test
    void testTaskDup() {
        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);
        assertTaskOperation(Operation.CREATE_TASK, User.PETYA, Task.Task1, Response.ERROR);

        sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1);
        assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1, Response.ERROR);

        sendTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task1);
        assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1, Response.CREATED);

        assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.TASK1, Response.CREATED);
    }

    /**
     * Пользователь может получить список всех задач любого другого пользователя, кроме удаленных.
     */
    @Test
    void testTaskListOk() {
        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);

        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task2);
        sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task2);
        sendTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task2);

        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task3);
        sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task3);

        assertTaskList(User.PETYA, User.VASYA, new Task[] {Task.Task1, Task.Task3});
        assertTaskList(User.PETYA, User.Vasya, new Task[] {});

    }

    /**
     * Пользователь может закрывать, удалять и заново открывать только свои задачи
     */
    @Test
    void testAccess() {
        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);
        assertTaskOperation(Operation.CLOSE_TASK, User.PETYA, Task.Task1, Response.ACCESS_DENIED);

        sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1);
        assertTaskOperation(Operation.DELETE_TASK, User.PETYA, Task.Task1, Response.ACCESS_DENIED);
    }

    /**
     * Задача проходит следующие состояния: CREATED <--> CLOSED -> DELETED.
     * При этом задача в статусе CREATED не может сразу перейти в DELETED.
     */
    @Test
    void testTaskLifecycle() {
        assertTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1, Response.ERROR);

        sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task2);
        assertTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task2, Response.ERROR);

        sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task2);
        assertTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task2, Response.DELETED);
    }

    private static String buildTaskListResponse(Task[] items) {
        return Arrays
                .stream(items)
                .map(Enum::name)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private void assertTaskList(User user, User owner, Task[] tasks) {
        var actual = sendMessage(user + " " + Operation.LIST_TASK + " " + owner);
        assertEquals(buildTaskListResponse(tasks), actual);
    }

    private String sendTaskOperation(Operation operation, User user, Task task) {
        return sendMessage(user + " " + operation + " " + task);
    }

    private void assertTaskOperation(Operation operation, User user, Task task, Response expected) {
        var actualResponse = sendTaskOperation(operation, user, task);
        assertEquals(expected.name(), actualResponse);
    }

    enum Task {
        Task1,
        Task2,
        Task3,
        TASK1
    }

    enum User {
        PETYA,
        VASYA,
        Vasya
    }

}