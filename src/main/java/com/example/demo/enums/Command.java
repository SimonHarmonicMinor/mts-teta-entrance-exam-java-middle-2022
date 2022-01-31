package com.example.demo.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Command {
    CREATE_TASK,
    DELETE_TASK,
    CLOSE_TASK,
    REOPEN_TASK,
    LIST_TASK,
    ;

    public static final Set<Command> ALL_COMMANDS = Arrays.stream(values()).collect(Collectors.toSet());
}
