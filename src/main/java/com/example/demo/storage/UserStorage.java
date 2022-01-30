package com.example.demo.storage;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByName(String userName);
}
