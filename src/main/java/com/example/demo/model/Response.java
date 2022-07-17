package com.example.demo.model;

import com.example.demo.enums.ResponseCodes;

public class Response {

    private ResponseCodes responseCode;
    private String[] args = new String[0];

    public ResponseCodes getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCodes responseCode) {
        this.responseCode = responseCode;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return args.length > 0 ? String.format("%s [%s]", responseCode.getResponse(),
                String.join(", ", args)) : responseCode.getResponse().equals(ResponseCodes.TASKS.getResponse()) ? String.format("%s []", responseCode.getResponse()) : responseCode.getResponse();
    }
}
