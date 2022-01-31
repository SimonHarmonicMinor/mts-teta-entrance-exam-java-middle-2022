package com.example.demo.repo;

import com.example.demo.entity.User;
import com.example.demo.exception.OtherErrorException;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> userMap = new HashMap<>() {{
        put("VASYA", new User("VASYA"));
        put("Yn", new User("Yn"));
    }};

    public User getUser(String nikName) {

        User user = userMap.get(nikName);
        if (user == null) {
            throw new OtherErrorException();
        }

        return user;
    }

    public boolean presenceUser(String nikName) {

        boolean isUser = userMap.containsKey(nikName);
        if (!isUser) {
            throw new OtherErrorException();
        }

        return true;
    }
}
