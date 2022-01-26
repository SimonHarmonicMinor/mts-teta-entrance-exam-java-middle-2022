package com.example.response;

import java.util.Arrays;

public class Response {


    public enum ResponseCodes{
        CREATED, DELETED, CLOSED, REOPENED, TASKS, WRONG_FORMAT, ACCESS_DENIED, ERROR
    }

    private ResponseCodes responseCode;

    private String[] args = new String[0];

    public ResponseCodes getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCodes responseCode) {
        this.responseCode = responseCode;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return Arrays.stream(args).count() > 0 ? responseCode + " [" +String.join(", ", args) + "]" : responseCode.toString();
    }


}
