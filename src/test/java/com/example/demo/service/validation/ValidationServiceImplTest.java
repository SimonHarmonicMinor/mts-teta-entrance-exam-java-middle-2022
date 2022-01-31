package com.example.demo.service.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationServiceImplTest {
    ValidationService validationService = new ValidationServiceImpl();

    @Test
    @DisplayName("Проверка валидации в случае передачи команды не соответствующей формату")
    void validateRequestIfInvalidCommandTest() {
        assertFalse(validationService.validateRequest(null));
        assertFalse(validationService.validateRequest("MyTask"));
        assertFalse(validationService.validateRequest("CREATE_TASK MyTask"));
        assertFalse(validationService.validateRequest("VASYA CREATE_TASK MyTask TASK TASK"));
        assertFalse(validationService.validateRequest("VASYA CREATE_TASk MyTask"));
        assertFalse(validationService.validateRequest("VASYA DELETe_TASK MyTask"));
        assertFalse(validationService.validateRequest("VASYA cLOSE_TASK MyTask"));
        assertFalse(validationService.validateRequest("VASYA REOpen_TASK MyTask"));
        assertFalse(validationService.validateRequest("VASYA List_TASK MyTask"));
    }

    @Test
    @DisplayName("Проверка успешной валидации")
    void validateRequestOkTest() {
        assertTrue(validationService.validateRequest("VASYA CREATE_TASK CleanRoom"));
        assertTrue(validationService.validateRequest("VASYA CREATE_TASK CleanRoom ARG"));
        assertTrue(validationService.validateRequest("PETYA DELETE_TASK CleanRoom"));
        assertTrue(validationService.validateRequest("VASYA LIST_TASK PETYA"));
        assertTrue(validationService.validateRequest("VASYA CLOSE_TASK CleanRoom"));
        assertTrue(validationService.validateRequest("VASYA REOPEN_TASK CleanRoom"));
    }
}