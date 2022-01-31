package com.example.demo;

import com.example.demo.services.task.TaskException;
import com.example.demo.services.task.components.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.services.task.components.Command.Type.*;
import static com.example.demo.services.task.components.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;


public class CommandTest {

    @Test
    @DisplayName("Проверка функционала команд")
    void checkCommand() {
        assertFalse(LIST_TASK.isNeedArg());
        assertTrue(CREATE_TASK.isNeedArg());
        assertTrue(DELETE_TASK.isNeedArg());
        assertTrue(CLOSE_TASK.isNeedArg());
        assertTrue(REOPEN_TASK.isNeedArg());

        assertEquals("LIST_TASK", LIST_TASK.getTitle());
        assertEquals("CREATE_TASK", CREATE_TASK.getTitle());
        assertEquals("DELETE_TASK", DELETE_TASK.getTitle());
        assertEquals("CLOSE_TASK", CLOSE_TASK.getTitle());
        assertEquals("REOPEN_TASK", REOPEN_TASK.getTitle());

        assertTrue(LIST_TASK.getStatusList().containsAll(List.of(CREATED, CLOSED, REOPENED)));
        assertEquals(3, LIST_TASK.getStatusList().size());
        assertTrue(DELETE_TASK.getStatusList().contains(CLOSED));
        assertTrue(CLOSE_TASK.getStatusList().containsAll(List.of(CREATED, REOPENED)));
        assertTrue(REOPEN_TASK.getStatusList().contains(CLOSED));

        try {
            new Command("", "");
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        try {
            new Command(CREATE_TASK.getTitle(), "");
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        try {
            new Command(CREATE_TASK.getTitle(), null);
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        assertEquals(CREATE_TASK.getTitle(), new Command(CREATE_TASK.getTitle(), "TASK").getType().getTitle());
        assertEquals("TASK", new Command(CREATE_TASK.getTitle(), "TASK").getArg());


        try {
            new Command(DELETE_TASK.getTitle(), "");
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        try {
            new Command(DELETE_TASK.getTitle(), null);
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        assertEquals(DELETE_TASK.getTitle(), new Command(DELETE_TASK.getTitle(), "TASK").getType().getTitle());
        assertEquals("TASK", new Command(DELETE_TASK.getTitle(), "TASK").getArg());


        try {
            new Command(CLOSE_TASK.getTitle(), "");
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        try {
            new Command(CLOSE_TASK.getTitle(), null);
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        assertEquals(CLOSE_TASK.getTitle(), new Command(CLOSE_TASK.getTitle(), "TASK").getType().getTitle());
        assertEquals("TASK", new Command(CLOSE_TASK.getTitle(), "TASK").getArg());


        try {
            new Command(REOPEN_TASK.getTitle(), "");
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        try {
            new Command(REOPEN_TASK.getTitle(), null);
        } catch (TaskException exception) {
            assertEquals(exception.getMessage(), TaskException.Type.WRONG_FORMAT.getMessage());
        }

        assertEquals(REOPEN_TASK.getTitle(), new Command(REOPEN_TASK.getTitle(), "TASK").getType().getTitle());
        assertEquals("TASK", new Command(REOPEN_TASK.getTitle(), "TASK").getArg());


        assertEquals(LIST_TASK.getTitle(), new Command(LIST_TASK.getTitle(), "").getType().getTitle());
        assertEquals("", new Command(LIST_TASK.getTitle(), "").getArg());

        assertEquals(LIST_TASK.getTitle(), new Command(LIST_TASK.getTitle(), null).getType().getTitle());
        assertEquals("", new Command(LIST_TASK.getTitle(), null).getArg());

        assertEquals(LIST_TASK.getTitle(), new Command(LIST_TASK.getTitle(), "TASK").getType().getTitle());
        assertEquals("TASK", new Command(LIST_TASK.getTitle(), "TASK").getArg());
    }
}
