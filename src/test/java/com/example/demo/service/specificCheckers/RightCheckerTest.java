package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.exception.RightException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.commandExecutor.CreateExecute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест должен:")
class RightCheckerTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("выполнять проверку права пользователя на совершение операции")
    void checkRightTest() {
        new CreateExecute(taskRepository,userRepository).execute(new Request("DASHA",Command.CREATE_TASK, "Task4"));
        Request request = new Request("DASHA", Command.CLOSE_TASK, "Task4");
        assertDoesNotThrow(() -> new RightChecker(taskRepository).check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение RightException")
    void checkARightThrowsRightExceptionTest() throws RightException {
        new CreateExecute(taskRepository,userRepository).execute(new Request("DASHA",Command.CREATE_TASK, "Task4"));
        Request request = new Request("SVETA", Command.CLOSE_TASK, "Task4");
        assertThrows(RightException.class, () -> new RightChecker(taskRepository).check(request));
    }

}