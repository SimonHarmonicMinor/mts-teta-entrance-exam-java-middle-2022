package com.example.demo;

import com.example.demo.model.enums.Command;
import com.example.demo.model.enums.Result;
import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static com.example.demo.model.enums.Command.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest extends AbstractServerTest {

    private static final String FIRST_VALID_USER_NAME = "Borisov";
    private static final String SECOND_VALID_USER_NAME = "Valeev";
    private static final String INVALID_USER_NAME = "Ilon_Mask";
    private static final String TASK_NAME = "MyTask";

    private static final String MESSAGE_TO_CREATE = buildMessage(FIRST_VALID_USER_NAME, CREATE_TASK, TASK_NAME);
    private static final String MESSAGE_TO_CLOSE = buildMessage(FIRST_VALID_USER_NAME, CLOSE_TASK, TASK_NAME);
    private static final String MESSAGE_TO_REOPEN = buildMessage(FIRST_VALID_USER_NAME, REOPEN_TASK, TASK_NAME);
    private static final String MESSAGE_TO_DELETE = buildMessage(FIRST_VALID_USER_NAME, DELETE_TASK, TASK_NAME);
    private static final String MESSAGE_TO_LIST = buildMessage(FIRST_VALID_USER_NAME);

    //region VALIDATION

    @Test
    public void sendTaskWhenNumberOfMessageFieldsLessThenTwo() {
        final String message = new StringJoiner(" ")
                .add(FIRST_VALID_USER_NAME)
                .toString();

        String response = sendMessage(message);
        assertEquals(Result.WRONG_FORMAT.toString(), response);
    }

    @Test
    public void sendTaskWhenNumberOfMessageFieldsMoreThenThree() {
        final String message = new StringJoiner(" ")
                .add(FIRST_VALID_USER_NAME)
                .add(CREATE_TASK.toString())
                .add(TASK_NAME)
                .add(TASK_NAME)
                .toString();

        String response = sendMessage(message);
        assertEquals(Result.WRONG_FORMAT.toString(), response);
    }

    @Test
    public void sendTaskWhenInvalidUser() {
        final String message = new StringJoiner(" ")
                .add(INVALID_USER_NAME)
                .add(CREATE_TASK.toString())
                .add(TASK_NAME)
                .toString();

        String response = sendMessage(message);
        assertEquals(Result.ACCESS_DENIED.toString(), response);
    }

    @Test
    public void sendTaskWhenInvalidCommand() {
        final String message = new StringJoiner(" ")
                .add(FIRST_VALID_USER_NAME)
                .add("Drop_database")
                .add(TASK_NAME)
                .toString();

        String response = sendMessage(message);
        assertEquals(Result.WRONG_FORMAT.toString(), response);
    }

    //endregion VALIDATION

    //region CREATE

    @Test
    public void sendTaskCreate() {
        String response = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), response);
    }

    @Test
    public void sendTaskCreateWhenTaskAlreadyExist() {
        String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        String s = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.ERROR.toString(), s);
    }

    @Test
    public void sendTaskCreateWhenTaskDeleted() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.DELETED.toString(), thirdResponse);

        final String fourthResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), fourthResponse);
    }

    //endregion CREATE

    //region CLOSE

    @Test
    public void sendTaskClose() {
        String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        String s = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), s);
    }

    @Test
    public void sendTaskCloseWhenTaskNotExist() {
        String s = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.ERROR.toString(), s);
    }

    @Test
    public void sendTaskCloseWhenTaskAlreadyClosed() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.ERROR.toString(), thirdResponse);
    }

    @Test
    public void sendTaskCloseWhenTaskDeleted() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.DELETED.toString(), thirdResponse);

        final String fourthResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.ERROR.toString(), fourthResponse);
    }

    @Test
    public void sendTaskCloseWhenAnotherUser() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String message = buildMessage(SECOND_VALID_USER_NAME, CLOSE_TASK, TASK_NAME);
        final String thirdResponse = sendMessage(message);
        assertEquals(Result.ERROR.toString(), thirdResponse);
    }

    //endregion CLOSE

    //region REOPEN

    @Test
    public void sendTaskReopen() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_REOPEN);
        assertEquals(Result.REOPENED.toString(), thirdResponse);
    }

    @Test
    public void sendTaskReopenWhenTaskCreated() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String fourthResponse = sendMessage(MESSAGE_TO_REOPEN);
        assertEquals(Result.ERROR.toString(), fourthResponse);
    }

    @Test
    public void sendTaskReopenWhenTaskDeleted() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.DELETED.toString(), thirdResponse);

        final String fourthResponse = sendMessage(MESSAGE_TO_REOPEN);
        assertEquals(Result.ERROR.toString(), fourthResponse);
    }

    @Test
    public void sendTaskReopenWhenTaskNotExist() {
        final String response = sendMessage(MESSAGE_TO_REOPEN);
        assertEquals(Result.ERROR.toString(), response);
    }

    @Test
    public void sendTaskReopenWhenAnotherUser() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String message = buildMessage(SECOND_VALID_USER_NAME, REOPEN_TASK, TASK_NAME);
        final String thirdResponse = sendMessage(message);
        assertEquals(Result.ERROR.toString(), thirdResponse);
    }

    //endregion REOPEN

    //region DELETE

    @Test
    public void sendTaskDelete() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.DELETED.toString(), thirdResponse);
    }

    @Test
    public void sendTaskDeleteWhenTaskCreated() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.ERROR.toString(), thirdResponse);
    }

    @Test
    public void sendTaskDeleteWhenAlreadyDeleted() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String thirdResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.DELETED.toString(), thirdResponse);

        final String fourthResponse = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.ERROR.toString(), fourthResponse);
    }

    @Test
    public void sendTaskDeleteWhenNotExist() {
        final String responce = sendMessage(MESSAGE_TO_DELETE);
        assertEquals(Result.ERROR.toString(), responce);
    }

    @Test
    public void sendTaskDeleteWhenAnotherUser() {
        final String firstResponse = sendMessage(MESSAGE_TO_CREATE);
        assertEquals(Result.CREATED.toString(), firstResponse);

        final String secondResponse = sendMessage(MESSAGE_TO_CLOSE);
        assertEquals(Result.CLOSED.toString(), secondResponse);

        final String message = buildMessage(SECOND_VALID_USER_NAME, DELETE_TASK, TASK_NAME);
        final String thirdResponse = sendMessage(message);
        assertEquals(Result.ERROR.toString(), thirdResponse);
    }

    //endregion DELETE

    //region LIST

    @Test
    public void sendTaskList() {
        sendMessage(MESSAGE_TO_CREATE);
        sendMessage(MESSAGE_TO_CLOSE);
        sendMessage(MESSAGE_TO_DELETE);

        sendMessage(buildMessage(FIRST_VALID_USER_NAME, CREATE_TASK, "firstTaskName"));
        sendMessage(buildMessage(FIRST_VALID_USER_NAME, CLOSE_TASK, "firstTaskName"));

        sendMessage(buildMessage(FIRST_VALID_USER_NAME, CREATE_TASK, "secondTaskName"));

        sendMessage(buildMessage(SECOND_VALID_USER_NAME, CREATE_TASK, "thirdTaskName"));

        final String response = sendMessage(MESSAGE_TO_LIST);
        assertEquals("[firstTaskName, secondTaskName, thirdTaskName]", response);
    }

    @Test
    public void sendTaskListWhenNoTasks() {
        final String response = sendMessage(MESSAGE_TO_LIST);
        assertEquals("[]", response);
    }

    //endregion LIST

    private static String buildMessage(final String userName, final Command command, final String taskName) {
        return new StringJoiner(" ")
                .add(userName)
                .add(command.toString())
                .add(taskName)
                .toString();
    }

    private static String buildMessage(final String userName) {
        return new StringJoiner(" ")
                .add(userName)
                .add(LIST_TASK.toString())
                .toString();
    }
}