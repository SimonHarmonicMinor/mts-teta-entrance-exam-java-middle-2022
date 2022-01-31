package com.example.demo.model;

public class Response {
    public enum Result {
        CREATED, DELETED, CLOSED, REOPENED, TASKS, WRONG_FORMAT, ACCESS_DENIED, ERROR
    }

    private boolean successful;
    private Result result;
    private String arg;

    public boolean isSuccessful() {
        return successful;
    }

    public Result getResult() {
        return result;
    }

    public String getArg() {
        return arg;
    }

    public Response(boolean successful) {
        this.successful = successful;
    }

    public Response(Result result, boolean successful) {
        this.result = result;
        this.successful = successful;
    }

    public Response(Result result, String arg, boolean successful) {
        this(result, successful);
        this.arg = arg;
    }

    @Override
    public String toString() {
        return result + (arg != null ? " " + arg : "");
    }
}
