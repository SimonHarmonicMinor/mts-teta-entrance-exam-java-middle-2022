package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Имплементация репозитория юзеров
 */

public class UserRepositoryImpl implements UserRepository {

    /**
     * HashMap для хранения юзеров, где ключ - имя юзера, значение - юзер
     */
    Map<String, User> userMap = new HashMap<>();

    /**
     * Создание юзера
     */
    @Override
    public void addUser(User user) {
        userMap.put(user.getName(), user);
    }

    /**
     * Получить юзера по его имени
     */
    @Override
    public User getUserByName(String userName) {
        return userMap.get(userName);
    }

    /**
     * Получить список всех юзеров
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
}
