package com.costa.socket.server.dao.impl;

import com.costa.socket.server.dao.UserDao;
import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDaoImpl implements UserDao {
    private final List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public Optional<User> findByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
