package com.example.demo;

import com.example.demo.entity.Task;
import com.example.demo.entity.enums.TaskState;
import com.example.demo.exceptions.StoreException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTest {
    private static TaskStore store;

    @BeforeAll
    static void init(){
        StoreTest.store = new TaskStore();
    }

    @Test
    @Order(1)
    void save(){
        Task task = new Task("Task1", "User1");
        assertDoesNotThrow( () -> store.save(task));
        assertDoesNotThrow( () -> {
            assertEquals(store.findByUserAndTask("User1", "Task1"), task);
        });
    }

    @Test
    @Order(2)
    void getList(){
        assertDoesNotThrow( () -> {
            assertEquals(
                    store.get("User1").toArray()[0],
                    store.findByUserAndTask("User1", "Task1"));
        });
    }

    @Test
    @Order(2)
    void updateState(){
        Task task = new Task("Task1", "User1");
        assertDoesNotThrow( () -> task.setState(TaskState.CLOSED));
        assertDoesNotThrow( () -> store.save(task));
        assertDoesNotThrow( () -> {
            Task find = (Task) store.findByUserAndTask("User1", "Task1");
            assertEquals(find.getState(), TaskState.CLOSED);
        });
    }

    @Test
    @Order(3)
    void delete(){
        Task task = new Task("Task1", "User1");
        assertDoesNotThrow( () -> { store.delete(task); });
    }

    @Test
    @Order(4)
    void deleteNotCreated(){
        Task task = new Task("Task1", "User2");
        assertThrows( StoreException.class, () -> { store.delete(task); });
    }

    @Test
    @Order(5)
    void getEmptyList(){
        assertArrayEquals(store.get("User1").toArray(), new Task[]{});
    }



}
