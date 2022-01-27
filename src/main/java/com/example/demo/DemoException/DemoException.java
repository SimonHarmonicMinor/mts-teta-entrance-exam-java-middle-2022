package com.example.demo.DemoException;

public class DemoException extends Exception{

    int status;

    public DemoException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
