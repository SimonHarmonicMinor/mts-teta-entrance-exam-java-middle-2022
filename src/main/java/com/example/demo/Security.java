package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.exception.EntityNotFoundException;

public class Security {

    private final Storage storage;

    public Security(Storage storage) {
        this.storage = storage;
    }

    public User getUser(String userName) throws EntityNotFoundException {
        return storage.getUserRepository().getUserByName(userName);
    }

    public User authenticate(String userName) {
        return storage.getUserRepository().createWithName(userName);
    }
}
