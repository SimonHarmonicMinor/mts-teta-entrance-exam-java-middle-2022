package com.costa.socket.server.daoservice;

import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByName(String name);
    List<User> findAll();
    boolean save(User user);
}
