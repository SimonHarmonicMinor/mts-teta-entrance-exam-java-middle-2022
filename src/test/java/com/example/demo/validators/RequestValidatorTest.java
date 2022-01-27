package com.example.demo.validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestValidatorTest {
    public final String CREATE = "User CREATE_TASK MyTask";
    public final String DELETE = "User CREATE_TASK MyTask";
    public final String CLOSE = "User CREATE_TASK MyTask";
    public final String REOPEN = "User CREATE_TASK MyTask";
    public final String LIST = "User CREATE_TASK MyTask";
    public final String INVALID_REQUEST = "User create_task MyTask";
    public final String INVALID_REQUEST_2 = "User CREATE_TASK";
    private static RequestValidator validator;

    @BeforeAll
    static void init() {
        validator = new RequestValidatorImpl();
    }

    @Test
    void createTest() {
        boolean valid = validator.isValid(CREATE);
        assertEquals(true, valid);
    }

    @Test
    void deleteTest() {
        boolean valid = validator.isValid(DELETE);
        assertEquals(true, valid);
    }

    @Test
    void closeTest() {
        boolean valid = validator.isValid(CLOSE);
        assertEquals(true, valid);
    }

    @Test
    void reopenTest() {
        boolean valid = validator.isValid(REOPEN);
        assertEquals(true, valid);
    }

    @Test
    void listTest() {
        boolean valid = validator.isValid(LIST);
        assertEquals(true, valid);
    }

    @Test
    void isValid() {
        boolean valid = validator.isValid(INVALID_REQUEST);
        assertEquals(false, valid);
    }

    @Test
    void isInvalid() {
        boolean valid = validator.isInvalid(INVALID_REQUEST_2);
        assertEquals(true, valid);
    }
}