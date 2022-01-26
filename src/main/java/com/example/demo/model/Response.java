package com.example.demo.model;

import com.example.demo.enums.Result;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Response {
    private final Result result;
    private List<String> tasks;

    public Response(Result result) {
        this.result = result;
    }

    public Response(Result result, List<String> tasks) {
        this.result = result;
        this.tasks = tasks;
    }

    public String getAsString() {
        return tasks == null ? result.toString() : result.toString() + " " + Optional.ofNullable(tasks).map(Objects::toString).orElse(null);
    }
}
