package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест должен:")
class ListExecuteTest {

    UserRepository userRepository = new UserRepositoryImpl();
    TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("выполнять команду получения листа задач пользователя")
    void listExecuteTest() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "GoForAWalk"));
        Request request = new Request("VASYA", Command.LIST_TASK, "DASHA");
        String result = new ListExecute(userRepository).execute(request);
        assertEquals("TASKS [GoForAWalk]", result);
    }

    @Test
    @DisplayName("возвращать ERROR")
    void listExecuteTestReturnError() {
        Request request = new Request("VASYA", Command.LIST_TASK, "DASHA");
        String result = new ListExecute(userRepository).execute(request);
        assertEquals("TASKS []", result);
    }

}