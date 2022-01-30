package com.example.demo.responses;

public class Response {
    private String result;
    private String arg;

    public Response(String result, String arg) {
        this.result = result;
        this.arg = arg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getContent() {
        String out = this.result;
        if (this.arg != null) {
            out = out + " " + this.arg;
        }
        return out;
    }
}
