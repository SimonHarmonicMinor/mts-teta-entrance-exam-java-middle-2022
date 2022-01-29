package com.costa.socket.server.dao;

import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.daoservice.UserService;
import com.costa.socket.server.daoservice.impl.TaskServiceImpl;
import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class TaskServiceTest {
    TaskService taskService;
    @Mock
    TaskDao taskDao;
    @Mock
    UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskDao, userService);
    }

    @Test
    void findAllByUser_Should_Return_List() {
        List<Task> tasks = Collections.singletonList(new Task());
        User user = new User("Test");

        given(taskDao.findAllByUser(user)).willReturn(tasks);

        assertEquals(taskService.findAllByUser(user), tasks);
        verify(taskDao).findAllByUser(user);
    }
}
