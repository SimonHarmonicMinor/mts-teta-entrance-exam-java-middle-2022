package com.example.demo;

public class Result {
    response resp;
    String arg;

    String toStr() {
        String r = this.resp.toString();
        if (this.arg != null)
            r = r + " " + this.arg;
        return  r;
    }
}

enum response{
    CREATED,
    CLOSED,
    DELETED,
    REOPENED,
    TASKS,
    WRONG_FORMAT,
    ACCESS_DENIED,
    ERROR
}