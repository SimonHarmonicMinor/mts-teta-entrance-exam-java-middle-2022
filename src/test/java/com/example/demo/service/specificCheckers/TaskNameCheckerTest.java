package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ErrorException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.commandExecutor.CreateExecute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест должен:")
class TaskNameCheckerTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("выполнять проверку имени задачи на уникальность")
    void checkTaskNameTest() {
        Request request = new Request("KATIA", Command.CREATE_TASK, "Task4");
        assertDoesNotThrow(() -> new TaskNameChecker(taskRepository).check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение ErrorException")
    void checkTaskNameThrowsErrorExceptionTest() throws ErrorException {
        new CreateExecute(taskRepository,userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task4"));
        Request request = new Request("KATIA", Command.CREATE_TASK, "Task4");
        assertThrows(ErrorException.class, () -> new TaskNameChecker(taskRepository).check(request));
    }

}