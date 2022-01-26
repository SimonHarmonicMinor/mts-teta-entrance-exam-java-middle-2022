package com.example.demo.models;

import com.example.demo.enums.Result;

import java.util.Collections;
import java.util.List;

public class Response {
    private Result result;
    private List<String> args;

    public Response(Result result) {
        this.result = result;
        this.args = Collections.emptyList();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
