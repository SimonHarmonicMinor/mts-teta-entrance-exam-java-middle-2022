package com.example.demo.service;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.specificCheckers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест должен:")
class RequestCheckerImplTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    List<RequestChecker> requestCheckerList = List.of(new RequiredFieldsChecker(),
            new UserNameChecker(), new TaskChecker(userRepository), new RightChecker(taskRepository),
            new AdditionalParamChecker(), new TaskNameChecker(taskRepository));

    @Test
    @DisplayName("пройтись по списку проверок, не выбрасывая исключений")
    void requestCheckerImplTest() {
        assertDoesNotThrow(() ->
                new RequestCheckerImpl(requestCheckerList).check(new Request("DASHA", Command.CREATE_TASK, "Task")));
    }

    @Test
    @DisplayName("пройтись по списку проверок, выбрасывая исключение FormatException")
    void requestCheckerImplThrowsFormatExceptionTest() throws FormatException {
        assertThrows(FormatException.class, () ->
                new RequestCheckerImpl(requestCheckerList).check(new Request(null, Command.CREATE_TASK, "Task")));
    }

}