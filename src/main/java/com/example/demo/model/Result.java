package com.example.demo.model;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Result {
    private final ResultType resultType;
    private final Collection<Task> tasks;

    public Result(ResultType resultType, Collection<Task> tasks) {
        this.resultType = resultType;
        this.tasks = tasks;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        StringJoiner s = new StringJoiner(", ");
        if (getTasks() != null) {
            return s.add(resultType + " " + getTasks().stream()
                    .filter(Objects::nonNull)
                    .map(Task::getName)
                    .collect(Collectors.toList())).toString();
        }
        return s.add(resultType.toString()).toString();
    }
}
