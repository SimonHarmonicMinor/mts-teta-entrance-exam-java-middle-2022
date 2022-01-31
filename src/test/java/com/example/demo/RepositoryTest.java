package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.State;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class RepositoryTest {

    @Test
    @DisplayName("Get user negative test")
    void test1(){
        UserRepository userRepository = new UserRepository(new ArrayList<>(Collections.emptyList()));
        User vasya = userRepository.getUser("VASYA");
        Assertions.assertNull(vasya);
    }

    @Test
    @DisplayName("Get user positive test")
    void test2(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("VASYA"));
        UserRepository userRepository = new UserRepository(users);
        User vasya = userRepository.getUser("VASYA");
        Assertions.assertNotNull(vasya);
        Assertions.assertEquals("VASYA", vasya.getName());
    }

    @Test
    @DisplayName("Get task negative test")
    void test3(){
        TaskRepository taskRepository = new TaskRepository(new ArrayList<>(Collections.emptyList()));
        Task todo = taskRepository.getTask("MyTask");
        Assertions.assertNull(todo);
    }

    @Test
    @DisplayName("Get task positive test")
    void test4(){
        TaskRepository taskRepository = new TaskRepository(new ArrayList<>(Collections.emptyList()));
        Task todo = taskRepository.addTask("MyTask");
        Task todo1 = taskRepository.getTask("MyTask");
        Assertions.assertEquals(todo, todo1);
    }

    @Test
    @DisplayName("Get deleted task negative test")
    void test5(){
        TaskRepository taskRepository = new TaskRepository(new ArrayList<>(Collections.emptyList()));
        Task todo = taskRepository.addTask("MyTask");
        todo.setState(State.DELETED);
        Task todo1 = taskRepository.getTask("MyTask");
        Assertions.assertNull(todo1);
    }

}
