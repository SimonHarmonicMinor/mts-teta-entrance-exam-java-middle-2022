package com.example.models;

import com.example.demo.enums.EAnswer;

public class Response {

    private String answer;

    public Response(EAnswer answer) {
        this.answer = answer.name();
    }

    public Response(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
