package com.example.demo.model;

public class Response {
    private final Result result;
    private String arg;

    public Response(Result result, String arg) {
        this.result = result;
        this.arg = arg;
    }
    public Response(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if (arg != null && !arg.isBlank()) {
            return result.val + " " + arg;
        } else {
            return result.val;
        }
    }
}
