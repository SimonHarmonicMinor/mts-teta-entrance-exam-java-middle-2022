package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findFirstByName(String name);
    List<User> findAll();
    void save(User user);
}
