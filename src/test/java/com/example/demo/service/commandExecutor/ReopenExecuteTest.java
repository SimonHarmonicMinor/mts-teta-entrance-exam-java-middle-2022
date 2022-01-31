package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Тест должен:")
class ReopenExecuteTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("выполнять команду открыть задачу вновь")
    void reopenExecuteTest() {
        taskRepository.addTask(new Task("GoToCafe", Status.CLOSED, "SASHA"));
        Request request = new Request("SASHA", Command.REOPEN_TASK, "GoToCafe");
        String result = new ReopenExecute(taskRepository).execute(request);
        assertEquals("REOPENED", result);
    }

    @Test
    @DisplayName("возвращать ERROR")
    void reopenExecuteTestReturnError() {
        taskRepository.addTask(new Task("GoToCafe", Status.CREATED, "SASHA"));
        Request request = new Request("SASHA", Command.REOPEN_TASK, "GoToCafe");
        String result = new ReopenExecute(taskRepository).execute(request);
        assertEquals("ERROR", result);
    }

}