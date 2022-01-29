package com.costa.socket.server;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static com.costa.socket.server.model.ResultStatus.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {
    @Test
    @Order(1)
    void modifyProcessTest() {
        assertAll(
                () -> assertEquals(CREATED.name(), sendMessage("Name CREATE_TASK M1")),
                () -> assertEquals(CREATED.name(), sendMessage("Name CREATE_TASK M2")),
                () -> assertEquals(CLOSED.name(), sendMessage("Name CLOSE_TASK M1")),
                () -> assertEquals(REOPENED.name(), sendMessage("Name REOPEN_TASK M1")),
                () -> assertEquals(CLOSED.name(), sendMessage("Name CLOSE_TASK M1")),
                () -> assertEquals(DELETED.name(), sendMessage("Name DELETE_TASK M1"))
        );
    }

    @Test
    @Order(2)
    void accessDeniedTest() {
        assertAll(
                () -> assertEquals(CREATED.name(), sendMessage("Name CREATE_TASK M3")),
                () -> assertEquals(ACCESS_DENIED.name(), sendMessage("Name CREATE_TASK M3")),
                () -> assertEquals(ACCESS_DENIED.name(), sendMessage("Name DELETE_TASK M3"))
        );
    }

    @Test
    @Order(3)
    void commandFormatTest() {
        assertAll(
                () -> assertEquals(WRONG_FORMAT.name(), sendMessage("Name CREATE_TASK M1 M2")),
                () -> assertEquals(WRONG_FORMAT.name(), sendMessage("Name DELETE_"))
        );
    }

    @Test
    @Order(4)
    void readTaskTest() {
        String[] tasks = new String[]{"M2", "M3"};
        assertEquals(TASKS.name() + " " + Arrays.toString(tasks),
                sendMessage("Name LIST_TASK Name"));
    }
}
