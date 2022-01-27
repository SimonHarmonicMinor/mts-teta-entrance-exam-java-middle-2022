package com.example.demo.repositories;

import com.example.demo.models.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepositoryImpl implements UserRepository {
    private static final Set<User> users = new HashSet<>();

    @Override
    public Optional<User> findByName(String name) {
        return users.stream()
                .filter(user -> name.equals(user.getName()))
                .findFirst();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }
}
