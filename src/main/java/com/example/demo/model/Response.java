package com.example.demo.model;

public class Response {
    public enum Result {
        CREATED, DELETED, CLOSED, REOPENED, TASKS, WRONG_FORMAT, ACCESS_DENIED, ERROR
    }

    private Result result;
    private String arg;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public Response(Result result) {
        this.result = result;
    }

    public Response(Result result, String arg) {
        this.result = result;
        this.arg = arg;
    }

    @Override
    public String toString() {
        return result + (arg != null ? " " + arg : "");
    }
}
