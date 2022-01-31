package com.example.demo.model;

import com.example.demo.model.types.ResponseType;

import java.util.List;

public class Response {

    private String result;
    private List<String> tasks;

    public Response(ResponseType responseType) {
        this.result = responseType.getResponseName();
    }

    public Response(ResponseType responseType, List<String> tasks) {
        this.result = responseType.getResponseName();
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        if (tasks != null) {
            return result + " " + tasks.toString();
        } else {
            return result;
        }
    }
}
