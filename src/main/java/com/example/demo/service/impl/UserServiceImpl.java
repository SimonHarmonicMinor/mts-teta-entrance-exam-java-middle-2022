package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.storage.UserStorage;
import com.example.demo.storage.impl.UserStorageImpl;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserStorage userStorage = new UserStorageImpl();

    @Override
    public Optional<User> findByName(String userName) {
        return userStorage.findByName(userName);
    }
}
