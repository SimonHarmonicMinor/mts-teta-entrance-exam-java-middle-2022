package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void test1() {
        String response = sendMessage("ILYA CREATE_TASK WriteTest");
        assertEquals("CREATED", response);
    }

    @Test
    void test2() {
        String response = sendMessage("ILYA create_task WriteTest");
        assertEquals("WRONG_FORMAT", response);
    }


    @Test
    void test3() {
        String response = sendMessage("ILYA CREATE_TASK");
        assertEquals("WRONG_FORMAT", response);
    }


    @Test
    void test4() {
        String response = sendMessage("ILYA CREATE_TASK WriteTest UNIT");
        assertEquals("CREATED UNIT", response);
    }

    @Test
    void test5() throws Exception {
        String response = sendMessage("ILYA CREATE_TASK WriteTest");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("ILYA CLOSE_TASK WriteTest");
        assertEquals("CLOSED", response);
    }

    @Test
    void test6() throws Exception {
        test5();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA DELETE_TASK WriteTest");
        assertEquals("DELETED", response);
    }

    @Test
    void test7() throws Exception {
        test5();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA REOPEN_TASK WriteTest");
        assertEquals("REOPENED", response);
    }

    @Test
    void test8() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA REOPEN_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void test9() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA DELETE_TASK WriteTest");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void test10() throws Exception {
        test6();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA REOPEN_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void test11() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA CREATE_TASK Coding");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("ILYA LIST_TASK ILYA");
        assertEquals("TASKS [Coding, WriteTest]", response);
    }

    @Test
    void test12() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("OLYA CREATE_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void test13() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("OLYA CLOSE_TASK WriteTest");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void test14() throws Exception {
        test5();
        afterEach();
        beforeEach();
        String response = sendMessage("OLYA DELETE_TASK WriteTest");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void test15() throws Exception {
        test5();
        afterEach();
        beforeEach();
        String response = sendMessage("OLYA REOPEN_TASK WriteTest");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void test16() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA CREATE_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void test17() {
        String response = sendMessage("ILYA CLOSE_TASK WriteTest");
        assertEquals("ERROR", response);
    }

    @Test
    void test18() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA CREATE_TASK Coding");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("OLYA LIST_TASK ILYA");
        assertEquals("TASKS [Coding, WriteTest]", response);
    }

    @Test
    void test19() throws Exception {
        test1();
        afterEach();
        beforeEach();
        String response = sendMessage("Ilya CREATE_TASK Coding");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("OLYA LIST_TASK ILYA");
        assertEquals("TASKS [WriteTest]", response);
        afterEach();
        beforeEach();
        response = sendMessage("ILYA LIST_TASK Ilya");
        assertEquals("TASKS [Coding]", response);
    }

    @Test
    void test20() throws Exception {
        test5();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA CREATE_TASK Coding");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("OLYA LIST_TASK ILYA");
        assertEquals("TASKS [Coding, WriteTest]", response);
    }

    @Test
    void test21() throws Exception {
        test6();
        afterEach();
        beforeEach();
        String response = sendMessage("ILYA CREATE_TASK Coding");
        assertEquals("CREATED", response);
        afterEach();
        beforeEach();
        response = sendMessage("OLYA LIST_TASK ILYA");
        assertEquals("TASKS [Coding]", response);
    }

    @Test
    void test22() throws Exception {
        test6();
        afterEach();
        beforeEach();
        String response = sendMessage("OLYA LIST_TASK ILYA");
        assertEquals("TASKS []", response);
    }
}