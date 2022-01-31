package com.example.demo;

import com.example.demo.services.task.TaskException;
import com.example.demo.services.task.components.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.services.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageTest {

    @Test
    @DisplayName("Проверка хранилища пользователей")
    void checkStorage() {
        assertNotNull(storage());
        assertNotNull(storage().getAllUser());

        checkAddUser(new User("checkStorage"));
    }

    public void checkAddUser(@NotNull User user) {
        String title = null;
        try {
            storage().checkUser(user.getUserTitle());
        } catch (TaskException taskException) {
            title = taskException.getMessage();
        }
        assertEquals(TaskException.Type.DEFAULT.getMessage(), title);
        storage().getAllUser().add(user);
        assertEquals(user.getUserTitle(), storage().checkUser(user.getUserTitle()).getUserTitle());
    }
}
