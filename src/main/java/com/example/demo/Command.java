package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum Command {
    CREATE_TASK("CREATE_TASK"),
    DELETE_TASK("DELETE_TASK"),
    CLOSE_TASK("CLOSE_TASK"),
    REOPEN_TASK("REOPEN_TASK"),
    LIST_TASK("LIST_TASK");
    private String task;

    Command(String task) {
        this.task = task;
    }

    private static final Map<String, Command> map;

    static {
        map = new HashMap<>();
        for (Command v : Command.values()) {
            map.put(v.task, v);
        }
    }

    private static final Set<String> tasks;

    static {
        tasks = new HashSet<>();
    }

    public static boolean checkUniqTask(String task) {
        return !tasks.contains(task);

    }

    public static boolean checkUniqTaskAndAdd(String task) {
        if (tasks.contains(task)) {
            return false;
        } else {
            tasks.add(task);
            return true;
        }
    }

    public static boolean checkCommand(String i) {
        return map.containsKey(i);
    }

    public static Command findByKey(String i) {
        return map.get(i);
    }
}
