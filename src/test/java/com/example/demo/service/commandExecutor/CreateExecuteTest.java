package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class CreateExecuteTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("выполнять команду создания задачи")
    void createExecuteTest() {
        Request request = new Request("VASYA", Command.CREATE_TASK, "GoShopping");
        String result = new CreateExecute(taskRepository, userRepository).execute(request);
        assertEquals("CREATED", result);
    }

}