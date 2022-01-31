package com.example.demo.service;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandlerImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.commandExecutor.*;
import com.example.demo.service.specificCheckers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Тест должен:")
class PlanOfTaskImplTest {

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

    List<RequestChecker> requestCheckerList = List.of(new RequiredFieldsChecker(), new CommandNameChecker(),
            new UserNameChecker(), new TaskChecker(userRepository), new RightChecker(taskRepository), new AdditionalParamChecker(),
            new TaskNameChecker(taskRepository));

    @Test
    @DisplayName("проверить запрос, вызвать нужную команду, вернуть результат CREATED")
    void planOfTaskImplReturn_CREATED_Test() {
        String result = new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap), new RequestCheckerImpl(requestCheckerList),
                new ExceptionHandlerImpl()).execute(new Request("DASHA", Command.CREATE_TASK, "Task"));
        assertEquals("CREATED", result);
    }

    @Test
    @DisplayName("проверить запрос, вызвать нужную команду, вернуть результат WRONG_FORMAT")
    void planOfTaskImplReturn_WRONG_FORMAT_Test() {
        String result = new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap), new RequestCheckerImpl(requestCheckerList),
                new ExceptionHandlerImpl()).execute(new Request("DASHA", Command.CREATE_TASK, null));
        assertEquals("WRONG_FORMAT", result);
    }

}