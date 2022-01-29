package com.example.demo.repository;

import com.example.demo.entity.User;

/**
 * Репозиторий для юзеров
 */

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void addUser(User user);

    Optional<User> getUserByName(String userName);

    List<User> getAllUsers();

}
