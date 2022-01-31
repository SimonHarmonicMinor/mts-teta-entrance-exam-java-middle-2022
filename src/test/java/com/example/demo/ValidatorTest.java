package com.example.demo;

import com.example.demo.exception.*;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.State;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.RequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

public class ValidatorTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    private RequestValidator requestValidator;

    public ValidatorTest() {
        MockitoAnnotations.openMocks(this);
        this.requestValidator = new RequestValidator(taskRepository, userRepository);
    }

    @Test
    @DisplayName("User not found exception (general)")
    void test1() {
        given(userRepository.getUser("VASYA")).willReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> requestValidator.getRequest("VASYA CREATE_TASK CleanRoom"));
    }

    @Test
    @DisplayName("Negative WrongCommandException")
    void test2 () {
        given(userRepository.getUser("VASYA")).willReturn(new User("VASYA"));
        Assertions.assertThrows(WrongCommandException.class, () -> requestValidator.getRequest("VASYA CRACK_TASK CleanRoom"));
    }
    @Test
    @DisplayName("Negative UserNotFoundException for listed user")
    void test3 () {
        given(userRepository.getUser("VASYA")).willReturn(new User("VASYA"));
        given(userRepository.getUser("PETYA")).willReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> requestValidator.getRequest("VASYA LIST_TASK PETYA"));
    }

    @Test
    @DisplayName("Negative TaskAlreadyExistsException)")
    void test4 () {
        given(userRepository.getUser("VASYA")).willReturn(new User("VASYA"));
        given(taskRepository.getTask("CleanRoom")).willReturn(new Task("CleanRoom"));
        Assertions.assertThrows(TaskAlreadyExistsException.class, () -> requestValidator.getRequest("VASYA CREATE_TASK CleanRoom"));
    }

    @Test
    @DisplayName("Negative ChangeStateException (created -> deleted)")
    void test5() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CREATED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        Assertions.assertThrows(ChangeStateException.class, () -> requestValidator.getRequest("VASYA DELETE_TASK CleanRoom"));
    }

    @Test
    @DisplayName("Negative ChangeStateException (deleted -> created")
    void test6() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.DELETED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        Assertions.assertThrows(ChangeStateException.class, () -> requestValidator.getRequest("VASYA REOPEN_TASK CleanRoom"));
    }

    @Test
    @DisplayName("Negative ChangeStateException (deleted -> created)")
    void test7() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.DELETED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        Assertions.assertThrows(ChangeStateException.class, () -> requestValidator.getRequest("VASYA CLOSE_TASK CleanRoom"));
    }



}
