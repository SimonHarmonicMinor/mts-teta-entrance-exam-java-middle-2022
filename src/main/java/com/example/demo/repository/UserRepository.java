package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.ArrayList;

public class UserRepository {

    private ArrayList<User> users;

    public UserRepository(ArrayList<User> users) {
        this.users = users;
    }

    public User getUser(String name) {
        return users.stream()
                .filter(u -> name.equals(u.getName()))
                .findAny()
                .orElse(null);
    }

}
