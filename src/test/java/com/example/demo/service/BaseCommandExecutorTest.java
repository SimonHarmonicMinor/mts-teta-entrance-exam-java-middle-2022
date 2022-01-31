package com.example.demo.service;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.commandExecutor.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class BaseCommandExecutorTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    Map<Command, CommandExecutor> commandExecutorMap = new HashMap<>();

    {
        commandExecutorMap.put(Command.CREATE_TASK, new
                CreateExecute(taskRepository, userRepository));
        commandExecutorMap.put(Command.LIST_TASK, new ListExecute(userRepository));
        commandExecutorMap.put(Command.REOPEN_TASK, new ReopenExecute(taskRepository));
        commandExecutorMap.put(Command.CLOSE_TASK, new CloseExecute(taskRepository));
        commandExecutorMap.put(Command.DELETE_TASK, new DeleteExecute(taskRepository));
    }

    @Test
    @DisplayName("выполнять выбор команды для выполнения, вернуть результат CREATED")
    void baseCommandExecutorReturn_CREATED_Test() {
        String result = new BaseCommandExecutor(commandExecutorMap).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        assertEquals("CREATED", result);
    }

    @Test
    @DisplayName("выполнять выбор команды для выполнения, вернуть результат CLOSED")
    void baseCommandExecutorReturn_CLOSED_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        String result = new BaseCommandExecutor(commandExecutorMap).execute(new Request("DASHA", Command.CLOSE_TASK, "Task"));
        assertEquals("CLOSED", result);
    }

    @Test
    @DisplayName("выполнять выбор команды для выполнения, вернуть результат REOPENED")
    void baseCommandExecutorReturn_REOPENED_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        new CloseExecute(taskRepository).execute(new Request("DASHA", Command.CLOSE_TASK, "Task"));
        String result = new BaseCommandExecutor(commandExecutorMap).execute(new Request("DASHA", Command.REOPEN_TASK, "Task"));
        assertEquals("REOPENED", result);
    }

    @Test
    @DisplayName("выполнять выбор команды для выполнения, вернуть результат TASKS")
    void baseCommandExecutorReturn_TASKS_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        String result = new BaseCommandExecutor(commandExecutorMap).execute(new Request("SVETA", Command.LIST_TASK, "DASHA"));
        assertEquals("TASKS [Task]", result);
    }

    @Test
    @DisplayName("выполнять выбор команды для выполнения, вернуть результат DELETED")
    void baseCommandExecutorReturn_DELETED_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        new CloseExecute(taskRepository).execute(new Request("DASHA", Command.CLOSE_TASK, "Task"));
        String result = new BaseCommandExecutor(commandExecutorMap).execute(new Request("DASHA", Command.DELETE_TASK, "Task"));
        assertEquals("DELETED", result);
    }
}