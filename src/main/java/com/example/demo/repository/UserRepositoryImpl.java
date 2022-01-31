package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private final Set<User> users = new HashSet<>();

    @Override
    public Optional<User> findFirstByName(String name) {
        return users.stream().filter(u -> u.getName().equals(name)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }
}
