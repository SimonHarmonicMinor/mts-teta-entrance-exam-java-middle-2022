package com.example.demo.database;

import com.example.demo.AbstractDatabaseSetUp;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest extends AbstractDatabaseSetUp {

    @Test
    public void checkUsers() {
        assertTrue(spyDb.checkUsers("user1"));
        assertFalse(spyDb.checkUsers(UNKNOWN));
    }

    @Test
    void addUser() {
        assertTrue(spyDb.addUser("user3"));

        Set<String> users = spyDb.getUsers();
        Map<String, Set<String>> createdTasks = spyDb.getCreatedTasks();
        Map<String, Set<String>> closedTasks = spyDb.getCreatedTasks();
        Map<String, Set<String>> deletedTasks = spyDb.getCreatedTasks();

        assertTrue(users.contains("user3"));
        assertEquals(Collections.emptySet(),createdTasks.get("user3"));
        assertEquals(Collections.emptySet(),closedTasks.get("user3"));
        assertEquals(Collections.emptySet(),deletedTasks.get("user3"));
    }

    @Test
    void checkTasks() {
        assertTrue(spyDb.checkTasks("task1"));
        assertFalse(spyDb.checkTasks(UNKNOWN));
    }

    @Test
    void addTask() {
        assertTrue(spyDb.addTask("user1", "task4"));
        assertFalse(spyDb.addTask("user1", "task1"));
    }

    @Test
    void closeTask() {
        assertTrue(spyDb.closeTask("user1", "task1"));
        assertFalse(spyDb.closeTask(UNKNOWN, "task1"));
        assertFalse(spyDb.closeTask("user1", UNKNOWN));
    }

    @Test
    void reopenTask() {
        assertTrue(spyDb.closeTask("user1", "task1"));

        assertFalse(spyDb.reopenTask(UNKNOWN, "task1"));
        assertFalse(spyDb.reopenTask("user1", UNKNOWN));
        assertTrue(spyDb.reopenTask("user1", "task1"));
    }

    @Test
    void deleteTask() {
        assertTrue(spyDb.closeTask("user1", "task1"));

        assertFalse(spyDb.deleteTask(UNKNOWN, "task1"));
        assertFalse(spyDb.deleteTask("user1", UNKNOWN));
        assertTrue(spyDb.deleteTask("user1", "task1"));
    }

    @Test
    void getTasks() {
        Set<String> expectedTasks = new LinkedHashSet<>();
        expectedTasks.add("task1");
        expectedTasks.add("task2");
        expectedTasks.add("task3");

        assertEquals(expectedTasks, spyDb.getTasks("user1"));
    }

    @Test
    void getUsers() {
        Set<String> expectedUsers = new LinkedHashSet<>();
        expectedUsers.add("user1");
        expectedUsers.add("user2");

        assertEquals(expectedUsers, spyDb.getUsers());
    }

    @Test
    void getCreatedTasks() {
        Map<String, Set<String>> expectedCreatedTasks = new HashMap<>();
        Set<String> user1Tasks = new LinkedHashSet<>();
        user1Tasks.add("task1");
        user1Tasks.add("task2");
        user1Tasks.add("task3");
        Set<String> user2Tasks = new LinkedHashSet<>();
        user2Tasks.add("task4");
        user2Tasks.add("task5");
        user2Tasks.add("task6");
        expectedCreatedTasks.put("user1", user1Tasks);
        expectedCreatedTasks.put("user2", user2Tasks);

        assertEquals(expectedCreatedTasks, spyDb.getCreatedTasks());
    }

    @Test
    void getClosedTasks() {
        assertTrue(spyDb.closeTask("user1", "task1"));
        assertTrue(spyDb.closeTask("user1", "task2"));

        Map<String, Set<String>> expectedClosedTasks = new HashMap<>();
        Set<String> user1Tasks = new LinkedHashSet<>();
        Set<String> user2Tasks = new LinkedHashSet<>();
        user1Tasks.add("task1");
        user1Tasks.add("task2");
        expectedClosedTasks.put("user1", user1Tasks);
        expectedClosedTasks.put("user2", user2Tasks);

        assertEquals(expectedClosedTasks, spyDb.getClosedTasks());
    }

    @Test
    void getDeletedTasks() {
        assertTrue(spyDb.closeTask("user1", "task1"));
        assertTrue(spyDb.closeTask("user1", "task2"));
        assertTrue(spyDb.deleteTask("user1", "task1"));
        assertTrue(spyDb.deleteTask("user1", "task2"));

        Map<String, Set<String>> expectedDeletedTasks = new HashMap<>();
        Set<String> user1Tasks = new LinkedHashSet<>();
        Set<String> user2Tasks = new LinkedHashSet<>();
        user1Tasks.add("task1");
        user1Tasks.add("task2");
        expectedDeletedTasks.put("user1", user1Tasks);
        expectedDeletedTasks.put("user2", user2Tasks);

        assertEquals(expectedDeletedTasks, spyDb.getDeletedTasks());
    }
}