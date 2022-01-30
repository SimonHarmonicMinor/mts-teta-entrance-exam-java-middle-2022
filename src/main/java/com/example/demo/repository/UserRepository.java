package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.*;

public class UserRepository {
    private final Set<User> users = new HashSet<>();

    public Optional<User> findFirstByName(String name) {
        return users.stream().filter(u -> u.getName().equals(name)).findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public boolean save(User user) {
        return users.add(user);
    }
}
