package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.State;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProcessingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

public class ProcessingTest {

    private ProcessingService processingService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;

    public ProcessingTest() {
        MockitoAnnotations.openMocks(this);
        this.processingService = new ProcessingService(taskRepository, userRepository);
    }

    @Test
    @DisplayName("Create test")
    void test() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CREATED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(null);
        String vasya_create_task = processingService.getResponse("VASYA CREATE_TASK CleanRoom");
        Assertions.assertEquals("CREATED", vasya_create_task);
    }

    @Test
    @DisplayName("Close test")
    void test2() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CREATED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        String vasya_close_task = processingService.getResponse("VASYA CLOSE_TASK CleanRoom");
        Assertions.assertEquals("CLOSED", vasya_close_task);
    }

    @Test
    @DisplayName("Reopen test")
    void test3() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CLOSED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        String vasya_reopen_task = processingService.getResponse("VASYA REOPEN_TASK CleanRoom");
        Assertions.assertEquals("REOPENED", vasya_reopen_task);
    }

    @Test
    @DisplayName("Delete test")
    void test4() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CLOSED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        String vasya_delete_task = processingService.getResponse("VASYA DELETE_TASK CleanRoom");
        Assertions.assertEquals("DELETED", vasya_delete_task);
    }

    @Test
    @DisplayName("List test")
    void test5() {
        User vasya = new User("VASYA");
        Task task = new Task("CleanRoom");
        task.setState(State.CLOSED);
        vasya.addTask(task);
        given(userRepository.getUser("VASYA")).willReturn(vasya);
        given(taskRepository.getTask("CleanRoom")).willReturn(task);
        String vasya_list_task = processingService.getResponse("VASYA LIST_TASK VASYA");
        Assertions.assertEquals("TASKS [CleanRoom]", vasya_list_task);
    }


}
