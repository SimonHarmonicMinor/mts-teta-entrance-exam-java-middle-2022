package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест должен:")
class UserNameCheckerTest {

    @Test
    @DisplayName("выполнять проверку имени юзера на написание в uppercase")
    void checkUserNameTest() {
        Request request = new Request("KATIA", Command.CREATE_TASK, "Task4");
        assertDoesNotThrow(() -> new UserNameChecker().check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение FormatException")
    void checkUserNameThrowsFormatExceptionTest() throws FormatException {
        Request request = new Request("katia", Command.CREATE_TASK, "Task4");
        assertThrows(FormatException.class, () -> new UserNameChecker().check(request));
    }

}