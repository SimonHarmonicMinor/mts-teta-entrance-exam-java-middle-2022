package com.example.demo.protocol;

import com.example.demo.entity.Task;

import java.util.List;
import java.util.Optional;

public enum Response {
    CREATED,
    DELETED,
    CLOSED,
    REOPENED,
    TASKS,
    WRONG_FORMAT,
    ACCESS_DENIED,
    ERROR;

    private String payload = "";

    public String getPayload() {
        return payload;
    }

    public Response setPayload(String payload) {
        this.payload = payload;

        return this;
    }

    @Override
    public String toString() {
        String result = super.toString();
        if (!payload.equals("")) {
            result += " " + payload;
        }

        return result;
    }
}
