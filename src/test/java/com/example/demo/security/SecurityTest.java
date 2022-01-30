package com.example.demo.security;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {
    private static Security security;

    @BeforeAll
    static void init() {
        security = new SecurityImpl();
    }

    @Test
    void okAccess() {
        User user = new User("User");
        Task task = new Task("Task", user);

        boolean access = security.checkAccess(user, task);

        assertEquals(true, access);
    }

    @Test
    void notAccess() {
        User user1 = new User("User1");
        User user2 = new User("User2");
        Task task = new Task("Task", user1);

        boolean access = security.checkAccess(user2, task);

        assertEquals(false, access);
    }
}