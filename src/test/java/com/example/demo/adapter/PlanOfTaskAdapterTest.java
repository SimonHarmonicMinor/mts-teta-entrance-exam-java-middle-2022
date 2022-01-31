package com.example.demo.adapter;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandlerImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.*;
import com.example.demo.service.commandExecutor.*;
import com.example.demo.service.specificCheckers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class PlanOfTaskAdapterTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();


    List<RequestChecker> requestCheckerList = List.of(new RequiredFieldsChecker(),
            new UserNameChecker(), new TaskChecker(userRepository), new RightChecker(taskRepository),
            new AdditionalParamChecker(), new TaskNameChecker(taskRepository));

    Map<Command, CommandExecutor> commandExecutorMap = new HashMap<>();

    {
        commandExecutorMap.put(Command.CREATE_TASK, new CreateExecute(taskRepository, userRepository));
        commandExecutorMap.put(Command.LIST_TASK, new ListExecute(userRepository));
        commandExecutorMap.put(Command.REOPEN_TASK, new ReopenExecute(taskRepository));
        commandExecutorMap.put(Command.CLOSE_TASK, new CloseExecute(taskRepository));
        commandExecutorMap.put(Command.DELETE_TASK, new DeleteExecute(taskRepository));
    }


    @Test
    @DisplayName("возвращать результат выполнения запроса CREATED")
    void planOfTaskAdapterReturn_CREATED_Test() {
        String line = "SVETA CREATE_TASK Task6";
        String result = new PlanOfTaskAdapter(new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap),
                new RequestCheckerImpl(requestCheckerList), new ExceptionHandlerImpl()), new ExceptionHandlerImpl()).execute(line);
        assertEquals("CREATED", result);
    }

    @Test
    @DisplayName("возвращать результат, вызванный исключением WRONG_FORMAT")
    void planOfTaskAdapterReturn_WRONG_FORMAT_Test() {
        String line = "SVETA CREATE_TASK ";
        String result = new PlanOfTaskAdapter(new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap),
                new RequestCheckerImpl(requestCheckerList), new ExceptionHandlerImpl()), new ExceptionHandlerImpl()).execute(line);
        assertEquals("WRONG_FORMAT", result);
    }

    @Test
    @DisplayName("возвращать результат, вызванный исключением ACCESS_DENIED")
    void planOfTaskAdapterReturn_ACCESS_DENIED_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("VASYA", Command.CREATE_TASK, "Task"));
        String line = "SVETA CLOSE_TASK Task";
        String result = new PlanOfTaskAdapter(new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap),
                new RequestCheckerImpl(requestCheckerList), new ExceptionHandlerImpl()), new ExceptionHandlerImpl()).execute(line);
        assertEquals("ACCESS_DENIED", result);
    }

    @Test
    @DisplayName("возвращать результат, вызванный исключением ERROR")
    void planOfTaskAdapterReturn_ERROR_Test() {
        new CreateExecute(taskRepository, userRepository).execute(new Request("VASYA", Command.CREATE_TASK, "Task"));
        String line = "SVETA CREATE_TASK Task";
        String result = new PlanOfTaskAdapter(new PlanOfTaskImpl(new BaseCommandExecutor(commandExecutorMap),
                new RequestCheckerImpl(requestCheckerList), new ExceptionHandlerImpl()), new ExceptionHandlerImpl()).execute(line);
        assertEquals("ERROR", result);
    }

}