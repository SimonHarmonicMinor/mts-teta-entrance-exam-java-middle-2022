package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO AbstractRepository
public class UserRepository {

    private final List<User> list = Collections.synchronizedList(new ArrayList<>());

    public User getUserByName(String userName) throws EntityNotFoundException {
        Optional<User> userOptional = list.stream().filter(x -> x.getName().equals(userName)).findAny();
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("User \"%s\" not exists", userName));
        }

        return userOptional.get();
    }

    public User createWithName(String userName)
    {
        try {
            return getUserByName(userName);
        } catch (EntityNotFoundException e) {
            User user = new User(userName);
            list.add(user);

            return user;
        }
    }
}
