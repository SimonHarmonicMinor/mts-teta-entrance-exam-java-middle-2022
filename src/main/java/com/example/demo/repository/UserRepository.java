package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для юзеров
 */
public interface UserRepository {

    void addUser(User user);

    Optional<User> getUserByName(String userName);

    List<User> getAllUsers();

}
