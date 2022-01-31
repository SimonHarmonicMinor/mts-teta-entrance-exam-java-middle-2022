package com.example.demo.util;

import com.example.demo.model.Request;
import com.example.demo.model.User;

public final class RequestParser {

    public static Request parse(String inputRequest) throws Exception {
        Request request = new Request();
        User user = new User(inputRequest.split(" ")[0]);
        if (!user.isValidUserName(user.getName()) || inputRequest.split(" ").length != 3) {
            throw new ParserException("Неверное имя в запросе или запрос неверной длины");
        }
        String command = inputRequest.split(" ")[1];
        String requestArgument = inputRequest.split(" ")[2];
        request.setUser(user);
        request.setCommand(command);
        request.setRequestArgument(requestArgument);
        return request;
    }
}
