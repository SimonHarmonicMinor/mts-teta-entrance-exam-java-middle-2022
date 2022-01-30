package com.example.demo.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class RequestObject {

    private String user;
    private Command command;
    private String arg;

    private static final Logger logger = LoggerFactory.getLogger(RequestObject.class);

    public static RequestObject parse(String request) throws ParseInvalidException {
        RequestObject requestObject = new RequestObject();
        String[] partOfRequest = request.split(" ");
        if(partOfRequest.length<3) {
            throw  new ParseInvalidException("Request is invalid.");
        }

        requestObject.user = partOfRequest[0].trim();
        requestObject.command = Command.valueOf(partOfRequest[1].trim());
        requestObject.arg = partOfRequest[2].trim();
        return requestObject;
    }


    public String getUser() {
        return user;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
