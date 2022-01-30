package com.example.demo.requests;

import com.example.demo.requests.exceptions.UnknownCommandException;
import com.example.demo.requests.exceptions.RequestValidateException;

import java.util.Arrays;

public class Request {
    public static final String CMD_CREATE_TASK = "CREATE_TASK";
    public static final String CMD_DELETE_TASK = "DELETE_TASK";
    public static final String CMD_CLOSE_TASK = "CLOSE_TASK";
    public static final String CMD_REOPEN_TASK = "REOPEN_TASK";
    public static final String CMD_LIST_TASK = "LIST_TASK";

    public final static String[] COMMANDS = {
            CMD_CREATE_TASK,
            CMD_DELETE_TASK,
            CMD_CLOSE_TASK,
            CMD_REOPEN_TASK,
            CMD_LIST_TASK,
    };

    protected final String input;
    protected String user;
    protected String command;
    protected String arg;

    public Request(String input) {
        this.input = input;
    }

    public String getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public void prepare() throws Exception {
        this.validateInput();
        this.fill();
        this.validateCommand();
    }

    protected void validateCommand() throws UnknownCommandException {
        if (!Arrays.asList(COMMANDS).contains(this.command)) {
            throw new UnknownCommandException();
        }
    }

    protected void validateInput() throws RequestValidateException {
        if (this.parse().length < 3) {
            throw new RequestValidateException();
        }
    }

    protected void fill() {
        String[] parsed = this.parse();
        this.user = parsed[0];
        this.command = parsed[1];
        this.arg = parsed[2];
    }

    protected String[] parse() {
        return this.input.split(" ", 3);
    }
}
