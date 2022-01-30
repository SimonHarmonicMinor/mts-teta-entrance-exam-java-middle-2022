package com.example.demo;

import assets.States;
import logic.TaskActions;
import objects.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    //1. Пользователь может добавлять, удалять, закрывать и заново открывать задачи
    @Test
    void createTaskStatusTest() {
        String response = sendMessage("VASYA CREATE_TASK Test01");
        assertEquals("CREATED", response);
    }

    @Test
    void closeTaskStatusTest() {
        Task testTask = new Task("Task1", States.CREATED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA CLOSE_TASK Task1");
        assertEquals("CLOSED", response);
    }

    @Test
    void deleteTaskStatusTest() {
        Task testTask = new Task("Task1", States.CLOSED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA DELETE_TASK Task1");
        assertEquals("DELETED", response);
    }

    @Test
    void reopenTaskStatusTest() {
        Task testTask = new Task("Task1", States.CLOSED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA REOPEN_TASK Task1");
        assertEquals("REOPENED", response);
    }

    //  2. Названия задач должны быть уникальными для всех пользователей (удаленные не учитываются).
    @Test
    void notUniqueTaskNameTest() {
        Task testTask = new Task("CleanRoom", States.CREATED, "VASYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("VASYA CREATE_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    @Test
    void notUniqueTaskNameTest_deletedTasksNotCount() {
        Task testTask = new Task("Test14", States.CREATED, "VASYA");
        TaskActions.deletedTasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("VASYA CREATE_TASK Test14");
        assertEquals("CREATED", response);
    }


    //3. Пользователь может получить список всех задач любого другого пользователя, кроме удаленных.

    @Test
    void getUserTasksTest() {
        Task testTask = new Task("Task1", States.CREATED, "PETYA");
        Task testTask2 = new Task("Task2", States.CREATED, "PETYA");
        Task testTask3 = new Task("Task3", States.CREATED, "VASYA");
        Task testTask4 = new Task("Task4", States.DELETED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);
        TaskActions.tasks.put(testTask2.getTaskName(), testTask2);
        TaskActions.tasks.put(testTask3.getTaskName(), testTask3);
        TaskActions.deletedTasks.put(testTask4.getTaskName(), testTask4);
        String response = sendMessage("VASYA LIST_TASK PETYA");
        assertEquals("TASKS [Task2, Task1]", response);
    }

    //4. Пользователь может закрывать, удалять и заново открывать только свои задачи

    @Test
    void userRightsTest_tryToClose() {
        Task testTask = new Task("CleanRoom", States.CREATED, "VASYA");
        TaskActions.tasks.put("CleanRoom", testTask);
        String response = sendMessage("PETYA CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void userRightsTest_tryToDelete() {
        Task testTask = new Task("CleanRoom", States.CLOSED, "VASYA");
        TaskActions.tasks.put("CleanRoom", testTask);
        String response = sendMessage("PETYA DELETE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void userRightsTest_tryToReopen() {
        Task testTask = new Task("CleanRoom", States.CLOSED, "VASYA");
        TaskActions.tasks.put("CleanRoom", testTask);
        String response = sendMessage("PETYA REOPEN_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    //5. ... задача в статусе `CREATED` не может сразу перейти в `DELETED`.
    @Test
    void deleteTaskInCreatedStatusTest() {
        Task testTask = new Task("Task1", States.CREATED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA DELETE_TASK Task1");
        assertEquals("ERROR", response);
    }


    // Задача же в `DELETED` больше не может переходить ни в какое состояние.
    @Test
    void closeDeletedTaskTest() {
        Task testTask = new Task("Task11", States.DELETED, "PETYA");
        TaskActions.deletedTasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA REOPEN_TASK Task11");
        assertEquals("ERROR", response);
    }

    @Test
    void reopenDeletedTaskTest() {
        Task testTask = new Task("Task12", States.DELETED, "PETYA");
        TaskActions.deletedTasks.put(testTask.getTaskName(), testTask);

        String response = sendMessage("PETYA CLOSE_TASK Task12");
        assertEquals("ERROR", response);
    }


    //5. Все команды, а также имена пользователей **регистрозависимые**
    @Test
    void caseSensitiveNameTest_tryToCloseTask() {
        Task testTask = new Task("CleanRoom", States.CREATED, "VASYA");
        TaskActions.tasks.put("CleanRoom", testTask);
        String response = sendMessage("Vasya CLOSE_TASK CleanRoom");
        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void caseSensitiveCommandsTest_tryToCloseTask() {
        Task testTask = new Task("CleanRoom", States.CREATED, "VASYA");
        TaskActions.tasks.put("CleanRoom", testTask);
        String response = sendMessage("VASYA Close_TASK CleanRoom");
        assertEquals("ERROR", response);
    }

    //Дополнительные тесты

    @Test
    void wrongFormatTest() {
        String response = sendMessage("VASYA CREATE_TASK");
        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void userMultiTaskTest() {
        Task testTask = new Task("Task1", States.CREATED, "PETYA");
        TaskActions.tasks.put(testTask.getTaskName(), testTask);
        String response = sendMessage("PETYA CREATE_TASK Task2");
        assertEquals("CREATED", response);
    }


}