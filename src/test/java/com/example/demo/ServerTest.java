package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.enums.Response.ACCESS_DENIED;
import static com.example.demo.enums.Response.CLOSED;
import static com.example.demo.enums.Response.CREATED;
import static com.example.demo.enums.Response.DELETED;
import static com.example.demo.enums.Response.ERROR;
import static com.example.demo.enums.Response.REOPENED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test
    @DisplayName("Should return CREATED")
    void test2() {
        // Given
        String request = "USER1 CREATE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(CREATED.name(), response);
    }

    @Test
    @DisplayName("Should return LIST size = 1")
    void test3() {
        // Given
        String request = "USER1 LIST_TASK USER1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals("TASKS [TASK1]", response);
    }

    @Test
    @DisplayName("Should return CLOSED")
    void test4() {
        // Given
        String request = "USER1 CLOSE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(CLOSED.name(), response);
    }

    @Test
    @DisplayName("Should return REOPENED")
    void test5() {
        // Given
        String request = "USER1 REOPEN_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(REOPENED.name(), response);
    }

    @Test
    @DisplayName("Should return ERROR cause can't delete non closed task")
    void test6() {
        // Given
        String request = "USER1 DELETE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(ERROR.name(), response);
    }

    @Test
    @DisplayName("Should return CLOSED again")
    void test7() {
        // Given
        String request = "USER1 CLOSE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(CLOSED.name(), response);
    }

    @Test
    @DisplayName("Should return DELETED again")
    void test8() {
        // Given
        String request = "USER1 DELETE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(DELETED.name(), response);
    }

    @Test
    @DisplayName("Should return CREATED")
    void testCase2_1() {
        // Given
        String request = "USER1 CREATE_TASK TASK2";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(CREATED.name(), response);
    }

    @Test
    @DisplayName("Should return CREATED")
    void testCase2_2() {
        // Given
        String request = "USER1 CREATE_TASK TASK3";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(CREATED.name(), response);
    }

    @Test
    @DisplayName("Should return LIST size = 2 for another user")
    void testCase2_3() {
        // Given
        String request = "USER2 LIST_TASK USER1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals("TASKS [TASK2, TASK3]", response);
    }

    @Test
    @DisplayName("Should ACCESS_DENIED for USER2 on closing")
    void testCase2_4() {
        // Given
        String request = "USER2 CLOSE_TASK TASK2";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(ACCESS_DENIED.name(), response);
    }

    @Test
    @DisplayName("Should ACCESS_DENIED for USER2 on deleting")
    void testCase2_5() {
        // Given
        String request = "USER2 DELETE_TASK TASK3";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(ACCESS_DENIED.name(), response);
    }
}