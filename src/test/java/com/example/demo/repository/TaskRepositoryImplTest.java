package com.example.demo.repository;

import com.example.demo.repository.entity.Task;
import com.example.demo.repository.entity.TaskStatus;
import com.example.demo.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

class TaskRepositoryImplTest {

    private TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    @DisplayName("Успешное создание задачи")
    void createTaskOkTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.CREATED);
        taskRepository.createTask(task);
    }

    @Test
    @DisplayName("Получение исключения при создании задачи : дубликат")
    void createTaskIfDuplicateNameTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.CREATED);
        taskRepository.createTask(task);

        var petya = new User("PETYA");
        Task task2 = new Task("MyTask", petya, TaskStatus.CREATED);
        Assertions.assertThrows(Exception.class, () -> taskRepository.createTask(task2));
    }

    @Test
    @DisplayName("Получение исключения при создании задачи : дубликат")
    void createTaskIfOkTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.DELETED);
        taskRepository.createTask(task);

        var petya = new User("PETYA");
        Task task2 = new Task("MyTask", petya, TaskStatus.CREATED);
        taskRepository.createTask(task2);
    }

    @Test
    @DisplayName("Проверка поиска по имени")
    void findTaskByNameOkTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.CREATED);
        taskRepository.createTask(task);

        Optional<Task> foundTask = taskRepository.findTaskByName(task.getName());
        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals(task, foundTask.get());

        Optional<Task> foundTask2 = taskRepository.findTaskByName("MyTask2");
        Assertions.assertTrue(foundTask2.isEmpty());
    }

    @Test
    @DisplayName("Проверка обновления задачи")
    void updateTaskOkTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.CREATED);
        taskRepository.createTask(task);
        task.setStatus(TaskStatus.CLOSED);
        taskRepository.updateTask(task);
    }

    @Test
    @DisplayName("Проверка поиска по пользователю")
    void findTasksByUserOkTest() throws Exception {
        var vasya = new User("VASYA");
        Task task = new Task("MyTask", vasya, TaskStatus.CREATED);
        taskRepository.createTask(task);

        Collection<Task> actualResponse = taskRepository.findTasksByUser("PETYA");
        Assertions.assertEquals(0, actualResponse.size());

        Collection<Task> actualResponse2 = taskRepository.findTasksByUser("VASYA");
        Assertions.assertEquals(1, actualResponse2.size());
    }
}