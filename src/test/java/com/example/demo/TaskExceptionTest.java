package com.example.demo;

import com.example.demo.services.task.TaskException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.services.task.TaskException.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskExceptionTest {

    @Test
    @DisplayName("Проверка исключений")
    void checkTaskException() {
        try {
            throw new TaskException();
        } catch (TaskException taskException) {
            assertEquals(taskException.getMessage(), DEFAULT.getMessage());
        }

        try {
            throw new TaskException(DEFAULT);
        } catch (TaskException taskException) {
            assertEquals(taskException.getMessage(), DEFAULT.getMessage());
        }

        try {
            throw new TaskException(WRONG_FORMAT);
        } catch (TaskException taskException) {
            assertEquals(taskException.getMessage(), WRONG_FORMAT.getMessage());
        }

        try {
            throw new TaskException(ACCESS_DENIED);
        } catch (TaskException taskException) {
            assertEquals(taskException.getMessage(), ACCESS_DENIED.getMessage());
        }
    }
}
