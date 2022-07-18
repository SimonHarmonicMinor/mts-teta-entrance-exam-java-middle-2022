package com.example.demo.dto;

import com.example.demo.router.Router;

public class Request {

    private String user;
    private String command;
    private String arg;
    private Boolean validate = true;

    public Request(String fullCommand) {
        String[] arrayFullString = fullCommand.split(" ");
        try {
            switch (arrayFullString[0]) {
                case (Router.__DELETE_ALL):
                    command = arrayFullString[0];
                    if (arrayFullString.length > 1) validate = false;
                    break;
                case (Router.LIST_TASK): {
                    command = arrayFullString[0];
                    arg = arrayFullString[1];
                    if (arrayFullString.length > 2) validate = false;
                    break;
                }
                default: {
                    user = arrayFullString[0];
                    command = arrayFullString[1];
                    arg = arrayFullString[2];
                    if ((command.equals(Router.__DELETE_ALL))) validate = false;
                    if (arrayFullString.length > 3) validate = false;
                }
            }
        } catch (Exception ex) {
            validate = false;
        }
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

    public Boolean getValidate() {
        return validate;
    }
}
