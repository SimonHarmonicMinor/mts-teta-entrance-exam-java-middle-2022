package com.example.demo.model;

import com.example.demo.SwpContext;

public abstract class SwpCommand {
    private final String userId;
    private String args;

    public SwpCommand(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public  abstract Result execute(SwpContext context);
}
