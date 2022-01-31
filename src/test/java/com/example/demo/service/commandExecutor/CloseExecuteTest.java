package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
public class CloseExecuteTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("выполнять команду закрытия задачи")
    void closeExecuteTest() {
        taskRepository.addTask(new Task("CleanRoom", Status.CREATED, "VASYA"));
        Request request = new Request("VASYA", Command.CLOSE_TASK, "CleanRoom");
        String result = new CloseExecute(taskRepository).execute(request);
        assertEquals("CLOSED", result);
    }

    @Test
    @DisplayName("возвращать ERROR")
    void closeExecuteTestReturnError() {
        taskRepository.addTask(new Task("CleanRoom", Status.DELETED, "VASYA"));
        Request request = new Request("VASYA", Command.CLOSE_TASK, "CleanRoom");
        String result = new CloseExecute(taskRepository).execute(request);
        assertEquals("ERROR", result);
    }

}
