package com.example.demo.repository;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.service.commandExecutor.CreateExecute;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class TaskRepositoryImplTest {

    @Test
    @DisplayName("выполнять добавление задачи в репозиторий")
    void addTaskTest() {
        assertDoesNotThrow(() -> new TaskRepositoryImpl().addTask(new Task
                ("Task", Status.CREATED, "DASHA")));
    }

    @Test
    @DisplayName("выполнять удаление задвчи из репозитория")
    void removeTaskTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        new TaskRepositoryImpl().addTask(task);
        assertDoesNotThrow(() -> new TaskRepositoryImpl().removeTask(task));
    }

    @Test
    @DisplayName("выполнять обновление задачи")
    void  updateTaskTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        new TaskRepositoryImpl().addTask(task);
        task.setStatus(Status.CLOSED);
        assertDoesNotThrow(() -> new TaskRepositoryImpl().updateTask(task));
    }

    @Test
    @DisplayName("выполнять получение задачи по имени")
    void  getTaskByNameTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        new TaskRepositoryImpl().addTask(task);
        assertEquals(Optional.of(task), new TaskRepositoryImpl().getTaskByName("Task"));
    }

    @Test
    @DisplayName("выполнить получение списка задач")
    void  getAllTasksTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        new TaskRepositoryImpl().addTask(task);
        assertEquals(List.of(task), new TaskRepositoryImpl().getAllTasks());
    }

}