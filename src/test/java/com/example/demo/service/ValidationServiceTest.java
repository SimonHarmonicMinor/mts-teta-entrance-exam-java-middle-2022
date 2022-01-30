package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationServiceTest {
    @Test
    void test01() {
        try {
            ValidationService.validateRequest("".split(" "));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void test02() {
        try {
            ValidationService.validateRequest("1".split(" "));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void test03() {
        try {
            ValidationService.validateRequest("1 2".split(" "));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void test04() {
        try {
            ValidationService.validateRequest("1 2 3".split(" "));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void test05() throws Exception {
        ValidationService.validateRequest("1 CREATE_TASK 2".split(" "));
    }

    @Test
    void test06() throws Exception {
        ValidationService.validateRequest("1 DELETE_TASK 2".split(" "));
    }

    @Test
    void test07() throws Exception {
        ValidationService.validateRequest("1 CLOSE_TASK 2".split(" "));
    }

    @Test
    void test08() throws Exception {
        ValidationService.validateRequest("1 REOPEN_TASK 2".split(" "));
    }

    @Test
    void test09() throws Exception {
        ValidationService.validateRequest("1 LIST_TASK 2".split(" "));
    }
}
