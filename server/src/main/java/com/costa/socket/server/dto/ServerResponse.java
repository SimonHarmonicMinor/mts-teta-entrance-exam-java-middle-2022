package com.costa.socket.server.dto;

import com.costa.socket.server.model.ResultStatus;
import com.costa.util.StringUtil;

public class ServerResponse<T> {
    private T status;
    private String arg;

    public ServerResponse(T status, String arg) {
        this.status = status;
        this.arg = arg;
    }

    public ServerResponse(T status) {
        this.status = status;
    }

    public ServerResponse(){}

    public static ServerResponse<ResultStatus> error() {
        ServerResponse<ResultStatus> response = new ServerResponse<>();
        response.status = ResultStatus.ERROR;

        return response;
    }

    public static ServerResponse<ResultStatus> create(ResultStatus status) {
        ServerResponse<ResultStatus> response = new ServerResponse<>();
        response.status = status;

        return response;
    }

    public static ServerResponse<ResultStatus> create(ResultStatus status, String arg) {
        ServerResponse<ResultStatus> response = new ServerResponse<>();
        response.status = status;
        response.arg = arg;

        return response;
    }

    public T getStatus() {
        return status;
    }

    public void setStatus(T status) {
        this.status = status;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!StringUtil.isEmpty(status))
            stringBuilder.append(getStatus());

        if (!StringUtil.isEmpty(arg))
            stringBuilder.append(" ").append(getArg());

        return stringBuilder.toString();
    }
}
