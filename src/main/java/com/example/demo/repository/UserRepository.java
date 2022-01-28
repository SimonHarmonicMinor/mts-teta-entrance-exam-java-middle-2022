package com.example.demo.repository;

import com.example.demo.entity.User;

/**
 * Репозиторий для юзеров
 */

import java.util.List;

public interface UserRepository {

    void addUser(User user);

    User getUserByName(String userName);

    List<User> getAllUsers();

}
