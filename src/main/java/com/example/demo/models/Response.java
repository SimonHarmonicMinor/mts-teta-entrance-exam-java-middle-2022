package com.example.demo.models;

import com.example.demo.enums.Result;

import java.util.Collections;
import java.util.Set;

public class Response {
    private Result result;
    private Set<String> args;

    public Response(Result result) {
        this.result = result;
        this.args = Collections.emptySet();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Set<String> getArgs() {
        return args;
    }

    public void setArgs(Set<String> args) {
        this.args = args;
    }
}
