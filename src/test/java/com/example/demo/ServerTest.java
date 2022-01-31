package com.example.demo;

import Models.Commands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    TestClass testClass;
    Commands commands;

    @BeforeEach
    void before() {
        testClass = new TestClass();
        commands = new Commands();
    }

    @Test
    void test() {
        String response = sendMessage("request");
        assertEquals("request", response);
    }

    @Test
    void test2() {
        String response = sendMessage("request2");
        assertEquals("request2", response);
    }

    @Test
    void test3() throws Exception {
        assertEquals(commands.getWrongFormat(), testClass.test("USER1 CREATE_TASK"));
        assertEquals(commands.getCreated(), testClass.test("USER1 CREATE_TASK TEST"));
        try {
            testClass.test("USER1 CREATE_TASK TEST");
        } catch (Exception e) {
            assertEquals(commands.getError(), e.getMessage());
        }
        assertEquals(commands.getClosed(), testClass.test("USER1 CLOSE_TASK TEST"));
        assertEquals(commands.getReopened(), testClass.test("USER1 REOPEN_TASK TEST"));
        assertEquals(commands.getClosed(), testClass.test("USER1 CLOSE_TASK TEST"));
        assertEquals(commands.getDeleted(), testClass.test("USER1 DELETE_TASK TEST"));
        try {
            testClass.test("USER2 DELETE_TASK TEST");
        } catch (Exception e) {
            assertEquals(commands.getAccessDenied(), e.getMessage());
        }
        try {
            testClass.test("USER2 DELETE_TASK TEST2");
        } catch (Exception e) {
            assertEquals(commands.getError(), e.getMessage());
        }
        assertEquals(commands.getTasks(), testClass.test("USER2 LIST_TASK USER1"));
        try {
            testClass.test("A B C");
        } catch (Exception e) {
            assertEquals(commands.getError(), e.getMessage());
        }
    }

}