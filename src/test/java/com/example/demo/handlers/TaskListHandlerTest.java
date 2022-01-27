package com.example.demo.handlers;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskListHandlerTest extends AbstractDatabaseSetUp {

    private final IHandler taskListHandler = new TaskListHandler();

    @BeforeEach
    void closeTasks(){
        spyDb.closeTask("user1", "task1");
    }

    @Test
    void execute() {
        Request requestFromUser1ToUser1 = new Request("user1", UNKNOWN, "user1");
        Request requestFromUser2ToUser1 = new Request("user2", UNKNOWN, "user1");

        Set<String> tasksSet = new LinkedHashSet<>();
        tasksSet.add("task1");
        tasksSet.add("task2");
        tasksSet.add("task3");
        Response expectedTasks = new Response(Result.TASKS);
        expectedTasks.setArgs(tasksSet);

        assertEquals(expectedTasks.getArgs(), taskListHandler.execute(requestFromUser1ToUser1, spyDb).getArgs());
        assertEquals(expectedTasks.getArgs(), taskListHandler.execute(requestFromUser2ToUser1, spyDb).getArgs());

        Mockito.verify(spyDb, Mockito.times(2)).getTasks("user1");
    }
}