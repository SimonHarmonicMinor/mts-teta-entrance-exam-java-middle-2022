package com.example.demo;

import com.example.demo.entity.enums.Result;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestResponseTest extends AbstractServerTest{

    @Test
    void wrongFormat(){
        String response = sendMessage("Vasia create_task task1");
        assertEquals(response, Result.WRONG_FORMAT.name());
    }

    @Test
    void withoutArguments(){
        String response = sendMessage("Vasia CREATE_TASK");
        assertEquals(response, Result.WRONG_FORMAT.name());
    }

    @Test
    @Order(1)
    void createTask(){
        String response = sendMessage("User1 CREATE_TASK MyTask");
        assertEquals(response, Result.CREATED.name());
    }

    @Test
    @Order(2)
    void createTaskWithSameName(){
        String response = sendMessage("User1 CREATE_TASK MyTask");
        assertEquals(response, Result.ERROR.name());
    }

    @Test
    @Order(3)
    void createTaskWithSameNameOtherUser(){
        String response = sendMessage("User2 CREATE_TASK MyTask");
        assertEquals(response, Result.ERROR.name());
    }

    @Test
    @Order(4)
    void createSecondTask(){
        String response = sendMessage("User1 CREATE_TASK MyTask1");
        assertEquals(response, Result.CREATED.name());
    }

    @Test
    @Order(5)
    void getSelfTasks(){
        String response = sendMessage("User1 LIST_TASK User1");
        assertEquals(response, "TASKS [MyTask, MyTask1]");
    }

    @Test
    @Order(5)
    void getEmptyTasks(){
        String response = sendMessage("User1 LIST_TASK User2");
        assertEquals(response, "TASKS []");
    }

    @Test
    @Order(5)
    void getTasksAnotherUser(){
        String response = sendMessage("User2 LIST_TASK User1");
        assertEquals(response, "TASKS [MyTask, MyTask1]");
    }

    @Test
    @Order(6)
    void closeOwnTask(){
        String response = sendMessage("User1 CLOSE_TASK MyTask");
        assertEquals(response, "CLOSED");
    }

    @Test
    @Order(7)
    void reopenOwnTask(){
        String response = sendMessage("User1 REOPEN_TASK MyTask");
        assertEquals(response, "REOPENED");
    }

    @Test
    @Order(8)
    void deleteOpenedOwnTask(){
        String response = sendMessage("User1 DELETE_TASK MyTask");
        assertEquals(response, "ERROR");
    }

    @Test
    @Order(9)
    void closeAgainOwnTask(){
        String response = sendMessage("User1 CLOSE_TASK MyTask");
        assertEquals(response, "CLOSED");
    }

    @Test
    @Order(10)
    void deleteClosedOwnTask(){
        String response = sendMessage("User1 DELETE_TASK MyTask");
        assertEquals(response, "DELETED");
    }

    @Test
    @Order(11)
    void openDeletedOwnTask(){
        String response = sendMessage("User1 REOPEN_TASK MyTask");
        assertEquals(response, "ERROR");
    }

    @Test
    @Order(12)
    void closeAnotherTask(){
        String response = sendMessage("User2 REOPEN_TASK MyTask1");
        assertEquals(response, "ACCESS_DENIED");
    }

    @Test
    @Order(12)
    void deleteAnotherTask(){
        String response = sendMessage("User2 CLOSE_TASK MyTask1");
        assertEquals(response, "ACCESS_DENIED");
    }

    @Test
    @Order(13)
    void createTaskWithDeletedName(){
        String response = sendMessage("User1 CREATE_TASK MyTask");
        assertEquals(response, "CREATED");
    }
}
