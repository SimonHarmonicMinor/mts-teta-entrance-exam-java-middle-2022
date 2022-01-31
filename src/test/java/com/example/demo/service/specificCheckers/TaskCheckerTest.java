package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.exception.FormatException;
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
class TaskCheckerTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("выполнять проверку наличия задачи в списке задач")
    void checkTaskCheckerTest() {
        taskRepository.addTask(new Task("Task5", Status.CREATED, "DASHA"));
        Request request = new Request("DASHA", Command.CLOSE_TASK, "Task5");
        assertDoesNotThrow(() -> new TaskChecker(userRepository).check(request));
    }

    @Test
    @DisplayName("выбрасывать исключение FormatException")
    void checkTaskCheckerExceptionTest() throws FormatException {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        Request request = new Request("DASHA", Command.CLOSE_TASK, "Task5");
        assertThrows(FormatException.class, () -> new TaskChecker(userRepository).check(request));
    }

}