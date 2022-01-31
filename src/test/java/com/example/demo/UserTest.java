package com.example.demo;

import com.example.demo.services.task.TaskException;
import com.example.demo.services.task.components.Task;
import com.example.demo.services.task.components.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.services.task.components.Command.Type.LIST_TASK;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("Проверка работы с пользователями")
    void checkUser() {
        User user = new User("checkUser");

        assertTrue(user.isNotAddedToStorage());
        new StorageTest().checkAddUser(user);
        assertFalse(user.isNotAddedToStorage());

        assertEquals("[]", user.getTaskListByString());

        try {
            user.checkTask("TASK", LIST_TASK.getStatusList());
        } catch (TaskException exception) {
            assertEquals(TaskException.Type.DEFAULT.getMessage(), exception.getMessage());
        }

        assertEquals("CREATED", user.addTask(new Task("TASK")));
        assertEquals("TASK", user.checkTask("TASK", LIST_TASK.getStatusList()).getTaskTitle());
        assertEquals("[TASK]", user.getTaskListByString());
    }
}
