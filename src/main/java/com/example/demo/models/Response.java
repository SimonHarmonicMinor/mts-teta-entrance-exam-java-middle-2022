package com.example.demo.models;

import java.util.List;

public class Response {
    private final Result result;
    private List<String> args;

    public Response(Result result) {
        this.result = result;
    }

    public Response(Result result, List<String> args) {
        this.result = result;
        this.args = args;
    }

    public static Response error() {
        return new Response(Result.ERROR);
    }

    public Result getResult() {
        return result;
    }

    public List<String> getArgs() {
        return args;
    }
}
