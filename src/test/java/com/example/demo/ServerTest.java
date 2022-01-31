package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void test() {
        String response = sendMessage("request");
        assertNotEquals("request", response);
    }

    @Test
    void test2() {
        String response = sendMessage("request2");
        assertNotEquals("request2", response);
    }

}
