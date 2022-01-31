package com.example.demo.repository;

import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class TaskRepositoryImplTest {

    TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("выполнять добавление задачи в репозиторий")
    void addTaskTest() {
        assertDoesNotThrow(() -> taskRepository.addTask(new Task
                ("Task", Status.CREATED, "DASHA")));
    }

    @Test
    @DisplayName("выполнять удаление задачи из репозитория")
    void removeTaskTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        taskRepository.addTask(task);
        assertDoesNotThrow(() -> taskRepository.removeTask(task));
    }

    @Test
    @DisplayName("выполнять обновление задачи")
    void  updateTaskTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        taskRepository.addTask(task);
        task.setStatus(Status.CLOSED);
        assertDoesNotThrow(() -> taskRepository.updateTask(task));
    }

    @Test
    @DisplayName("выполнять получение задачи по имени")
    void  getTaskByNameTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        taskRepository.addTask(task);
        assertEquals(Optional.of(task), taskRepository.getTaskByName("Task"));
    }

    @Test
    @DisplayName("выполнить получение списка задач")
    void  getAllTasksTest() {
        Task task = new Task
                ("Task", Status.CREATED, "DASHA");
        taskRepository.addTask(task);
        assertEquals(List.of(task), taskRepository.getAllTasks());
    }

}