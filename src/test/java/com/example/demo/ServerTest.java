package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.Task;
import com.example.demo.models.statuses.ClosedStatus;
import com.example.demo.models.statuses.DeletedStatus;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    //Пользователь может добавлять задачи
    @Test
    void testCreateTest() {
        String response = sendMessage("MIKE CREATE_TASK New Task");
        assertEquals("CREATED", response);
    }

    //Пользователь может удалять задачи
    @Test
    void testDeleteTest() {
        Task closed = new Task("MIKE", "New Task");
        closed.setStatus(new ClosedStatus(closed));
        Container.putTask("New Task", closed);
        String response = sendMessage("MIKE DELETE_TASK New Task");
        assertEquals("DELETED", response);
    }

    //Пользователь не может изменять удалять чужие задачи
    @Test
    void testCanNotDeleteAnotherUSerTask() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("ALEX DELETE_TASK New Task");
        assertEquals("ACCESS_DENIED", response);
    }

    //Пользователь не может закрывать чужие задачи
    @Test
    void testCanNotCloseAnotherUSerTask() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("ALEX CLOSE_TASK New Task");
        assertEquals("ACCESS_DENIED", response);
    }

    //Пользователь не может заного открывать чужие задачи
    @Test
    void testCanNotReopenAnotherUSerTask() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("ALEX REOPEN_TASK New Task");
        assertEquals("ACCESS_DENIED", response);
    }

    //Пользователь может закрывать задачи
    @Test
    void testCloseTest() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("MIKE CLOSE_TASK New Task");
        assertEquals("CLOSED", response);
    }

    //Пользователь может заново открывать задачи
    @Test
    void testReopenTest() {
        Task task = new Task("MIKE", "New Task");
        task.setStatus(new ClosedStatus(task));
        Container.putTask("New Task", task);
        String response = sendMessage("MIKE REOPEN_TASK New Task");
        assertEquals("REOPENED", response);
    }

    //Названия задач должны быть уникальными для всех пользователей (удаленные не учитываются).
    @Test
    void testUniqTitle() {
        Task task = new Task("MIKE", "New Task");
        task.setStatus(new DeletedStatus(task));
        Container.putTask("New Task", task);
        String response = sendMessage("MIKE CREATE_TASK New Task");
        assertEquals("CREATED", response);
    }

    //Пользователь может получить список всех задач любого другого пользователя, кроме удаленных.
    @Test
    void testGetTaskList() {
        Container.putTask("New Task1", new Task("MIKE", "New Task1"));
        Container.putTaskIndex("MIKE", "New Task1");
        Container.putTask("New Task2", new Task("MIKE", "New Task2"));
        Container.putTaskIndex("MIKE", "New Task2");
        Container.putTask("New Task3", new Task("MIKE", "New Task3"));
        Container.putTaskIndex("MIKE", "New Task3");
        Task deleted = new Task("MIKE", "New Task4");
        deleted.setStatus(new DeletedStatus(deleted));
        Container.putTask("New Task4", deleted);
        Container.putTaskIndex("MIKE", "New Task4");
        String response = sendMessage("ALEX LIST_TASK MIKE");
        assertEquals("LIST [New Task1, New Task2, New Task3]", response);
    }

    //Задача проходит следующие состояния: CREATED <--> CLOSED -> DELETED.
    @Test
    void testCreatedToDeleted() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("MIKE DELETE_TASK New Task");
        assertEquals("CAN NOT CHANGE STATUS", response);
    }

    //Задача проходит следующие состояния: CREATED <--> CLOSED -> DELETED.
    @Test
    void testDeletedToClosed() {
        Task deleted = new Task("MIKE", "New Task");
        deleted.setStatus(new DeletedStatus(deleted));
        Container.putTask("New Task", deleted);
        String response = sendMessage("MIKE CLOSE_TASK New Task");
        assertEquals("CAN NOT CHANGE STATUS", response);
    }

    //Имена пользователей регистрозависимые
    @Test
    void testUserNameCase() {
        Container.putTask("New Task", new Task("MIKE", "New Task"));
        String response = sendMessage("MIKE LIST_TASK MiKe");
        assertEquals("LIST []", response);
    }

    //Команды регистрозависимые
    @Test
    void testCommandCase() {
        String response = sendMessage("MIKE create_task Task 1");
        assertEquals("UNKNOWN COMMAND", response);
    }

    //Неверный формат запроса
    @Test
    void testRequestFormat() {
        String response = sendMessage("MIKE CREATE_TASK");
        assertEquals("WRONG REQUEST FORMAT", response);
    }
}