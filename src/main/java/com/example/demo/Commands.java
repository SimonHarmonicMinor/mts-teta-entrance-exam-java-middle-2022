package com.example.demo;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Commands {
    String CREATE_TASK = "CREATE_TASK";
    String DELETE_TASK = "DELETE_TASK";
    String CLOSE_TASK = "CLOSE_TASK";
    String REOPEN_TASK = "REOPEN_TASK";
    String LIST_TASK = "LIST_TASK";

    HashSet<String> COMMANDS = Stream.of(
                    Commands.CLOSE_TASK, Commands.CREATE_TASK, Commands.DELETE_TASK, Commands.LIST_TASK, Commands.REOPEN_TASK
            )
            .collect(Collectors.toCollection(HashSet::new));
}
