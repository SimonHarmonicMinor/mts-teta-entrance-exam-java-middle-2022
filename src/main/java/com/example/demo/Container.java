package com.example.demo;

import com.example.demo.models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public final class Container {

    private static Container instance;

    private String user;
    private HashMap<String, Task> tasks = new HashMap<>();
    private HashMap<String, ArrayList<String>> taskIndexesByUser = new HashMap<>();

    private Container() {
    }

    public static Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public static void putTask(String key, Task task) {
        Container.getInstance().tasks.put(key, task);
    }

    public static Task getTask(String key) {
        return Container.getInstance().tasks.get(key);
    }

    public static void putTaskIndex(String user, String key) {
        Container.getInstance().taskIndexesByUser.computeIfAbsent(user, k -> new ArrayList<String>());
        Container.getInstance().taskIndexesByUser.get(user).add(key);
    }

    public static ArrayList<String> getTaskIndexes(String user) {
        return Container.getInstance().taskIndexesByUser.get(user);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (this.user == null) {
            this.user = user;
        }
    }

    public static void flush() {
        Container.getInstance().tasks = new HashMap<>();
        Container.getInstance().taskIndexesByUser = new HashMap<>();
    }
}
