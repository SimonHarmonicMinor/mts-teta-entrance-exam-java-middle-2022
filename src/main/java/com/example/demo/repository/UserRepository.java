package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.List;

public interface UserRepository {

    void addUser(User user);

    User getUserByName(String userName);

    List<User> getAllUsers();

}
