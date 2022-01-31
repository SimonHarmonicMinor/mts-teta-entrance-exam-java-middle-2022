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
class DeleteExecuteTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("выполнять команду удаления задачи")
    void deleteExecuteTest() {
        taskRepository.addTask(new Task("Task1", Status.CLOSED, "LENA"));
        Request request = new Request("LENA", Command.DELETE_TASK, "Task1");
        String result = new DeleteExecute(taskRepository).execute(request);
        assertEquals("DELETED", result);
    }

    @Test
    @DisplayName("возвращать ERROR")
    void deleteExecuteTestReturnError() {
        taskRepository.addTask(new Task("Task1", Status.CREATED, "LENA"));
        Request request = new Request("LENA", Command.DELETE_TASK, "Task1");
        String result = new DeleteExecute(taskRepository).execute(request);
        assertEquals("ERROR", result);
    }

}