package com.example.demo.services;

import com.example.demo.services.task.TaskException;
import com.example.demo.services.task.data.User;

import java.util.ArrayList;
import java.util.List;


public class Storage {
    private volatile static Storage INSTANCE;
    private static final List<User> USERS = new ArrayList<>();

    private Storage() {
    }

    public static Storage storage() {
        if (INSTANCE == null)
            synchronized (Storage.class) {
                if (INSTANCE == null)
                    INSTANCE = new Storage();
            }

        return INSTANCE;
    }

    public List<User> getAllUser() throws TaskException {
        return USERS;
    }

    public User checkUser(String title) throws TaskException {
        return USERS
                .stream()
                .filter(s -> s.getUserTitle().equals(title))
                .findFirst()
                .orElseThrow(TaskException::new);
    }

}
