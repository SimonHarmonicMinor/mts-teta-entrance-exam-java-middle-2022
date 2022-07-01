package com.example.demo;

public class Request {
    String  user;
    String  command;
    String  arg;

    public void setParams(String user, String command, String arg){
        this.user = user;
        this.command = command;
        this.arg = arg;
    }


}
