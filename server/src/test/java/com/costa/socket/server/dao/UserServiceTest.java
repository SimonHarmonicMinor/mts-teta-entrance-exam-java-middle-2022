package com.costa.socket.server.dao;

import com.costa.socket.server.dao.UserDao;
import com.costa.socket.server.daoservice.UserService;
import com.costa.socket.server.daoservice.impl.UserServiceImpl;
import com.costa.socket.server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    @Mock
    UserDao userDao;
    UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    void findByName() {
        Optional<User> user = Optional.of(new User("Test"));

        given(userDao.findByName("Test")).willReturn(user);

        assertEquals(user, userService.findByName("Test"));
        verify(userDao).findByName(any());
    }
}
