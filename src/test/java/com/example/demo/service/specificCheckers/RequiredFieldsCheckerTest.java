package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест должен:")
class RequiredFieldsCheckerTest {

    @Test
    @DisplayName("выполнять проверку запроса на заполнение обязательных полей")
    void checkRequiredFieldsTest() {
        Request request = new Request("SVETA", Command. CREATE_TASK, "Task2");
        assertDoesNotThrow(() -> new RequiredFieldsChecker().check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение FormatException")
    void checkRequiredFieldsThrowsFormatExceptionTest() throws FormatException {
        Request request = new Request(null, Command.LIST_TASK, "dima");
        assertThrows(FormatException.class, () -> new RequiredFieldsChecker().check(request));
    }

}