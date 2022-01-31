package com.example.demo.service.task;

import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.entity.Task;
import com.example.demo.repository.entity.TaskStatus;
import com.example.demo.repository.entity.User;
import com.example.demo.service.dto.CommandParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class TaskServiceImplTest {
    private TaskRepository taskRepositoryMock = Mockito.mock(TaskRepository.class);
    private TaskService taskServiceSpy = Mockito.spy(new TaskServiceImpl(taskRepositoryMock));

    @Test
    @DisplayName("Проверка создания задачи")
    void createTaskTest() throws Exception {
        var expectedUser = new User("VASYA");
        Task expectedTask = new Task("MyTask", expectedUser, TaskStatus.CREATED);
        Mockito.doNothing().when(taskRepositoryMock).createTask(expectedTask);
        var commandParams = new CommandParams("VASYA", "CREATE_TASK", "MyTask");
        String actualResponse = taskServiceSpy.createTask(commandParams);
        Assertions.assertEquals("CREATED", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ERROR при удалении: отсутствие задачи")
    void deleteTaskIfTaskNotFoundTest() throws Exception {
        var commandParams = new CommandParams("VASYA", "DELETE_TASK", "MyTask");
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.empty());
        String actualResponse = taskServiceSpy.deleteTask(commandParams);
        Assertions.assertEquals("ERROR", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ACCESS_DENIED при удалении: отсутствие прав")
    void deleteTaskIfAccessDeniedTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("PETYA", "DELETE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CREATED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.deleteTask(commandParams);
        Assertions.assertEquals("ACCESS_DENIED", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ERROR при удалении: задача в статусе CREATED")
    void deleteTaskIfCreatedStatusTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("VASYA", "DELETE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CREATED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.deleteTask(commandParams);
        Assertions.assertEquals("ERROR", actualResponse);
    }

    @Test
    @DisplayName("Проверка успешного удаления")
    void deleteTaskOkTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("VASYA", "DELETE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CLOSED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.deleteTask(commandParams);
        Assertions.assertEquals("DELETED", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ERROR при закрытии: в случае отсутствия задачи")
    void closeTaskTaskNotFoundTest() throws Exception {
        var commandParams = new CommandParams("VASYA", "DELETE_TASK", "MyTask");
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.empty());
        String actualResponse = taskServiceSpy.closeTask(commandParams);
        Assertions.assertEquals("ERROR", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ACCESS_DENIED при закрытии: отсутствие прав")
    void closeTaskIfAccessDeniedTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("PETYA", "CLOSE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CREATED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.closeTask(commandParams);
        Assertions.assertEquals("ACCESS_DENIED", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения ERROR при закрытии: задача в статусе DELETED")
    void closeTaskIfDeletedStatusTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("VASYA", "CLOSE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.DELETED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.closeTask(commandParams);
        Assertions.assertEquals("ERROR", actualResponse);
    }

    @Test
    @DisplayName("Проверка успешного закрытия")
    void closeTaskOkTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("VASYA", "CLOSE_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CREATED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.closeTask(commandParams);
        Assertions.assertEquals("CLOSED", actualResponse);
    }

    @Test
    @DisplayName("Проверка успешного переоткрытия задачи")
    void reOpenTaskOkTest() throws Exception {
        var expectedUser = new User("VASYA");
        var commandParams = new CommandParams("VASYA", "REOPEN_TASK", "MyTask");
        Task actualTask = new Task("MyTask", expectedUser, TaskStatus.CLOSED);
        Mockito.when(taskRepositoryMock.findTaskByName(commandParams.getTaskName())).thenReturn(Optional.of(actualTask));
        String actualResponse = taskServiceSpy.reOpenTask(commandParams);
        Assertions.assertEquals("REOPENED", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения TASKS [] при получении списка задач: не найдены задачи")
    void listTaskIfNotFoundTest() {
        var commandParams = new CommandParams("VASYA", "REOPEN_TASK", null, "PETYA");
        Mockito.when(taskRepositoryMock.findTasksByUser(commandParams.getArg())).thenReturn(List.of());
        String actualResponse = taskServiceSpy.listTask(commandParams);
        Assertions.assertEquals("TASKS []", actualResponse);
    }

    @Test
    @DisplayName("Проверка получения TASKS [] при получении списка задач: не найдены задачи !DELETED или свои DELETED")
    void listTaskIfTaskDeletedAnotherUserTest() {
        var anotherUser = new User("VASYA");
        Task task = new Task("MyTask", anotherUser, TaskStatus.DELETED);
        var commandParams = new CommandParams("PETYA", "LIST_TASK", null, "VASYA");
        Mockito.when(taskRepositoryMock.findTasksByUser(commandParams.getArg())).thenReturn(List.of(task));
        String actualResponse = taskServiceSpy.listTask(commandParams);
        Assertions.assertEquals("TASKS []", actualResponse);

        Task task2 = new Task("MyTask2", anotherUser, TaskStatus.DELETED);
        var commandParams2 = new CommandParams("VASYA", "LIST_TASK", null, "IGOR");
        Mockito.when(taskRepositoryMock.findTasksByUser(commandParams2.getArg())).thenReturn(List.of(task, task2));
        String actualResponse2 = taskServiceSpy.listTask(commandParams2);
        Assertions.assertEquals("TASKS []", actualResponse2);
    }

    @Test
    @DisplayName("Проверка успешного получения TASKS [] при получении списка задач")
    void listTaskIfOkTest() {
        var anotherUser = new User("VASYA");
        var anotherUser2 = new User("PETYA");
        Task task1 = new Task("MyTask1", anotherUser, TaskStatus.DELETED);
        Task task2 = new Task("MyTask2", anotherUser, TaskStatus.DELETED);
        Task task3 = new Task("MyTask3", anotherUser2, TaskStatus.DELETED);
        Task task4 = new Task("MyTask4", anotherUser2, TaskStatus.CLOSED);
        Task task5 = new Task("MyTask5", anotherUser2, TaskStatus.CREATED);

        var commandParams = new CommandParams("PETYA", "LIST_TASK", null, "VASYA");
        Mockito.when(taskRepositoryMock.findTasksByUser(commandParams.getArg())).thenReturn(List.of(task1, task2, task3, task4, task5));
        String actualResponse = taskServiceSpy.listTask(commandParams);
        Assertions.assertEquals("TASKS [MyTask4, MyTask5]", actualResponse);

        var commandParams2 = new CommandParams("VASYA", "LIST_TASK", null, "VASYA");
        Mockito.when(taskRepositoryMock.findTasksByUser(commandParams.getArg())).thenReturn(List.of(task1, task2, task3, task4, task5));
        String actualResponse2 = taskServiceSpy.listTask(commandParams2);
        Assertions.assertEquals("TASKS [MyTask1, MyTask2, MyTask4, MyTask5]", actualResponse2);
    }
}