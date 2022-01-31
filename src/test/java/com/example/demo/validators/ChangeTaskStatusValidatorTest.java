package com.example.demo.validators;

import com.example.demo.exceptions.AnyOtherErrorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.demo.enums.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeTaskStatusValidatorTest {

    private static ChangeTaskStatusValidator validator;

    @BeforeAll
    static void beforeAll() {
        validator = ChangeTaskStatusValidator.getInstance();
    }

    @Test
    void validateCreatedToDeletedThrowException() {
        assertThrows(AnyOtherErrorException.class, () -> validator.validate(CREATED, DELETED));
    }

    @Test
    void validateDeletedToReopenedThrowException() {
        assertThrows(AnyOtherErrorException.class, () -> validator.validate(DELETED, REOPENED));
    }

    @Test
    void validateCreatedToReopenedThrowException() {
        assertThrows(AnyOtherErrorException.class, () -> validator.validate(CREATED, REOPENED));
    }

    @Test
    void validateCreatedToClosed() {
        assertDoesNotThrow(() -> validator.validate(CREATED, CLOSED));
    }

    @Test
    void validateClosedToDeleted() {
        assertDoesNotThrow(() -> validator.validate(CLOSED, DELETED));
    }

    @Test
    void validateClosedToReopened() {
        assertDoesNotThrow(() -> validator.validate(CLOSED, REOPENED));
    }
}