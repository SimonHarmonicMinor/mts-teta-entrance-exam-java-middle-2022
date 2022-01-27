package com.example.demo.models;

import com.example.demo.enums.Result;

import java.util.Set;

public class Response {
    private final Result result;
    private Set<String> tasks;

    public Response(Result result) {
        this.result = result;
    }

    public Response(Result result, Set<String> tasks) {
        this.result = result;
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return result.toString() + (tasks == null ? "" : " " + tasks.toString());
    }
}
