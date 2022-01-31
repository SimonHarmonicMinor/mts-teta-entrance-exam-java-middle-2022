package com.example.demo.validators;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.AnyOtherErrorException;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.demo.enums.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class AccessValidatorTest {

    @Test
    void userHasAccessTest() {
        Task task = new Task("TestTask1", CREATED);
        User user = new User("TestUser", task);

        Set<Task> tasks = new LinkedHashSet<>();
        tasks.add(new Task("TestTask2", CREATED));
        tasks.add(new Task("TestTask3", REOPENED));
        tasks.add(new Task("TestTask1", CLOSED));

        user.setTasks(tasks);

        assertDoesNotThrow(() -> AccessValidator.validate(user, task));
    }

    @Test
    void deletedTaskNotAccepted() {
        Task task = new Task("TestTask1", CREATED);
        User user = new User("TestUser", task);

        Set<Task> tasks = new LinkedHashSet<>();
        tasks.add(new Task("TestTask2", CREATED));
        tasks.add(new Task("TestTask3", REOPENED));
        tasks.add(new Task("TestTask1", DELETED));

        user.setTasks(tasks);

        assertThrows(AccessDeniedException.class, () -> AccessValidator.validate(user, task));
    }
}