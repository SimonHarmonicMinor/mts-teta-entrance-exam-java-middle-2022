package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.enums.Response.WRONG_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerValidationTest extends AbstractServerTest {

    @Test
    @DisplayName("Should return WRONG_FORMAT for empty request")
    void test1() {
        // Given
        String request = "";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }

    @Test
    @DisplayName("Should return WRONG_FORMAT for wrong request size = 1")
    void test2() {
        // Given
        String request = "request";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }

    @Test
    @DisplayName("Should return WRONG_FORMAT for wrong request size = 2")
    void test3() {
        // Given
        String request = "request request2";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }

    @Test
    @DisplayName("Should return WRONG_FORMAT for wrong request size = 4")
    void test4() {
        // Given
        String request = "request request2 request3, request4";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }

    @Test
    @DisplayName("Should return WRONG_FORMAT for wrong command")
    void test5() {
        // Given
        String request = "USER1 UPDATE_TASK TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }

    @Test
    @DisplayName("Should return WRONG_FORMAT for lowercase command")
    void test6() {
        // Given
        String request = "USER1 create_task TASK1";

        // When
        String response = sendMessage(request);

        // Then
        assertEquals(WRONG_FORMAT.name(), response);
    }
}
