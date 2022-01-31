package com.example.demo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class ExceptionHandlerImplTest {

    @Test
    @DisplayName("возвращать результат WRONG_FORMAT")
    void exceptionHandlerReturn_WRONG_FORMAT_Test() {
        String result = new ExceptionHandlerImpl().handle(new FormatException("Неправильный формат написания имени"));
        assertEquals("WRONG_FORMAT", result);
    }

    @Test
    @DisplayName("возвращать результат ERROR")
    void exceptionHandlerReturn_ERROR_Test() {
        String result = new ExceptionHandlerImpl().handle(new ErrorException("Имя задачи не уникально"));
        assertEquals("ERROR", result);
    }

    @Test
    @DisplayName("возвращать результат ACCESS_DENIED")
    void exceptionHandlerReturn_ACCESS_DENIED_Test() {
        String result = new ExceptionHandlerImpl().handle(new RightException("Нет прав для совершения действия"));
        assertEquals("ACCESS_DENIED", result);
    }

}