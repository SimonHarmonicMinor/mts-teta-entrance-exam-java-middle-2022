package com.garipov.socket.server.dao;

import com.garipov.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByName(String name);
    List<User> findAll();
}
