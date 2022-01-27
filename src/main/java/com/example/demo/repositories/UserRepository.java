package com.example.demo.repositories;

import com.example.demo.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);

    User save(User user);
}
