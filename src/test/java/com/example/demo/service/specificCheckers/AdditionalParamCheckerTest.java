package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест должен:")
class AdditionalParamCheckerTest {

    @Test
    @DisplayName("выполнять проверку написания имени пользователя в доп.параметре")
    void checkEAdditionalParamTest() {
        Request request = new Request("SVETA", Command.LIST_TASK, "DIMA");
        assertDoesNotThrow(() -> new AdditionalParamChecker().check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение FormatException")
    void checkEAdditionalParamReturnWrongFormatTest() throws FormatException {
        Request request = new Request("SVETA", Command.LIST_TASK, "dima");
        assertThrows(FormatException.class, () -> new AdditionalParamChecker().check(request));
    }

}