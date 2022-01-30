package com.example.demo.storage.impl;

import com.example.demo.model.User;
import com.example.demo.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserStorageImpl implements UserStorage {
    private final Set<User> storage = new HashSet<>();

    public UserStorageImpl() {
        storage.addAll(
                List.of(
                        new User("Dima"),
                        new User("Irina"),
                        new User("Igor")
                )
        );
    }

    @Override
    public Optional<User> findByName(String userName) {
        return storage.stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst();
    }
}
