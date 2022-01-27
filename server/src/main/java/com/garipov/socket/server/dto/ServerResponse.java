package com.garipov.socket.server.dto;

import com.garipov.socket.server.model.ResultStatus;

import java.util.List;

public class ServerResponse {
    private final ResultStatus result;
    private List<String> tasks;

    public ServerResponse(ResultStatus result) {
        this.result = result;
    }

    public ServerResponse(ResultStatus result, List<String> tasks) {
        this.result = result;
        this.tasks = tasks;
    }

    public ResultStatus getResult() {
        return result;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "result=" + result +
                ", tasks=" + tasks +
                '}';
    }
}
