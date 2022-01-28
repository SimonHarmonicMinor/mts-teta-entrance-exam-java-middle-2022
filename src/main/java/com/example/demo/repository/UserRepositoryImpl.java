package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    Map<String, User> userMap = new HashMap<>();

    @Override
    public void addUser(User user) {
        userMap.put(user.getName(), user);
    }

    @Override
    public User getUserByName(String userName) {
        return userMap.get(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
}
