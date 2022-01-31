package com.example.demo.service;

import com.example.demo.AbstractServerTest;
import com.example.demo.exception.DemoException;
import com.example.demo.model.Command;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RequestHandlerTest extends AbstractServerTest {

    @Test
    void testValidateRequestFormat_requestConsistOfOneArgument() {
        String response = sendMessage("test_user");

        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void testValidateRequestFormat_requestConsistOfTwoArgument() {
        String response = sendMessage("test_user command");

        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void testValidateRequestFormat_wrongCommand() {
        String response = sendMessage("test_user wrong_command argument");

        assertEquals("WRONG_FORMAT", response);
    }

    @Test
    void testValidateRequestFormat_success() {
        validateRequestFormat_success("CREATE_TASK");
        validateRequestFormat_success("DELETE_TASK");
        validateRequestFormat_success("CLOSE_TASK");
        validateRequestFormat_success("REOPEN_TASK");
        validateRequestFormat_success("LIST_TASK");
    }

    @Test
    void testAuthorization_notNeedCheckAuth() {
        authorization("CREATE_TASK");
        authorization("LIST_TASK");
    }

    @Test
    void testAuthorization_taskNotFound() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenThrow(new DemoException("Task with name " + taskName + " do not found"));

        String response = sendMessage(userName + " CLOSE_TASK " + taskName);

        assertEquals("ERROR", response);
    }

    @Test
    void testAuthorization_accessDenied() {
        String taskName = "test_task";
        String userName1 = "test_user_1";
        String userName2 = "test_user_2";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName1, TaskStatus.CREATED));

        String response = sendMessage(userName2 + " CLOSE_TASK " + taskName);

        assertEquals("ACCESS_DENIED", response);
    }

    @Test
    void testTakeActionWithTask_getTasks() {
        String taskName1 = "test_task_1";
        String taskName2 = "test_task_2";
        String userName1 = "test_user_1";
        String userName2 = "test_user_2";
        List<Task> tasks = List.of(new Task(taskName1, userName2, TaskStatus.CREATED), new Task(taskName2, userName2, TaskStatus.CLOSED));

        when(taskService.getActualTasksByUserName(userName2)).thenReturn(tasks);

        String response = sendMessage(userName1 + " LIST_TASK " + userName2);

        assertEquals("TASKS [" + taskName1 + ", " + taskName2 + "]", response);
        verify(taskService).getActualTasksByUserName(userName2);
    }

    @Test
    void testTakeActionWithTask_createTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.createTask(userName, taskName)).thenReturn(new Task(taskName, userName, TaskStatus.CREATED));

        String response = sendMessage(userName + " CREATE_TASK " + taskName);

        assertEquals("CREATED", response);
        verify(taskService).createTask(userName, taskName);
    }

    @Test
    void testTakeActionWithTask_createTaskWithNotUniqueName() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.createTask(userName, taskName)).thenThrow(new DemoException(""));

        String response = sendMessage(userName + " CREATE_TASK " + taskName);

        assertEquals("ERROR", response);
        verify(taskService).createTask(userName, taskName);
    }

    @Test
    void testTakeActionWithTask_deleteTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName, TaskStatus.CLOSED));
        when(taskService.changeTaskStatus(taskName, Command.DELETE_TASK)).thenReturn(TaskStatus.DELETED);

        String response = sendMessage(userName + " DELETE_TASK " + taskName);

        assertEquals("DELETED", response);
        verify(taskService).changeTaskStatus(taskName, Command.DELETE_TASK);
    }

    @Test
    void testTakeActionWithTask_deleteCreatedTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName, TaskStatus.CREATED));
        when(taskService.changeTaskStatus(taskName, Command.DELETE_TASK)).thenThrow(new DemoException("Task in the CREATED status cannot immediately change to DELETED: taskName = " + taskName));

        String response = sendMessage(userName + " DELETE_TASK " + taskName);

        assertEquals("ERROR", response);
        verify(taskService).changeTaskStatus(taskName, Command.DELETE_TASK);
    }

    @Test
    void testTakeActionWithTask_reopenTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName, TaskStatus.CLOSED));
        when(taskService.changeTaskStatus(taskName, Command.REOPEN_TASK)).thenReturn(TaskStatus.CREATED);

        String response = sendMessage(userName + " REOPEN_TASK " + taskName);

        assertEquals("REOPENED", response);
        verify(taskService).changeTaskStatus(taskName, Command.REOPEN_TASK);
    }

    @Test
    void testTakeActionWithTask_closeTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName, TaskStatus.CREATED));
        when(taskService.changeTaskStatus(taskName, Command.CLOSE_TASK)).thenReturn(TaskStatus.CLOSED);

        String response = sendMessage(userName + " CLOSE_TASK " + taskName);

        assertEquals("CLOSED", response);
        verify(taskService).changeTaskStatus(taskName, Command.CLOSE_TASK);
    }

    @Test
    void testTakeActionWithTask_doSmthWithDeletedTask() {
        String taskName = "test_task";
        String userName = "test_user";

        when(taskService.getTaskByName(taskName)).thenReturn(new Task(taskName, userName, TaskStatus.DELETED));
        when(taskService.changeTaskStatus(taskName, Command.CLOSE_TASK)).thenThrow(new DemoException("A task in DELETED can no longer transition to any state: taskName = " + taskName));

        String response = sendMessage(userName + " CLOSE_TASK " + taskName);

        assertEquals("ERROR", response);
        verify(taskService).changeTaskStatus(taskName, Command.CLOSE_TASK);
    }

    private void validateRequestFormat_success(String command) {
        String response = sendMessage("user " + command + " taskName");

        assertNotEquals("WRONG_FORMAT", response);
    }

    private void authorization(String command) {
        String response = sendMessage("user " + command + " taskName");

        assertNotEquals("ACCESS_DENIED", response);
    }

}