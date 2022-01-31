package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест должен:")
class CommandNameCheckerTest {

    @Test
    @DisplayName("выполнять проверку запроса на правильность команды")
    void checkCommandNameTest() {
        Request request = new Request("PASHA", Command.CREATE_TASK, "WriteAMessage");
        assertDoesNotThrow(() -> new CommandNameChecker().check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение FormatException")
    void checkCommandNameThrowsFormatExceptionTest() throws FormatException {
        Request request = new Request("PASHA", Command.valueOf("CREATE"), "WriteAMessage");
        assertThrows(FormatException.class, () -> new CommandNameChecker().check(request));
    }

}