package com.example.demo.mappers;

import com.example.demo.model.*;

public class Mapper {

    public static Request mapToRequest(String requestString) {
        String[] requestArray = requestString.split(" ");
        Request request = new Request();
        if (requestArray.length == 1) {
            request.setCommand(Command.valueOfCode(requestArray[0]));
            return request;
        }
        if (requestArray.length == 2) {
            for (int i = 0; i < requestArray.length; i++) {
                switch (i) {
                    case 0: {
                        request.setCommand(Command.valueOfCode(requestArray[i]));
                        break;
                    }
                    case 1: {
                        request.setUser(new User(requestArray[i]));
                        break;
                    }
                }
            }
            return request;
        }
        for (int i = 0; i < requestArray.length; i++) {
            switch (i) {
                case 0: {
                    request.setUser(new User(requestArray[i]));
                    break;
                }
                case 1: {
                    request.setCommand(Command.valueOfCode(requestArray[i]));
                    break;
                }
                case 2: {
                    request.setTask(mapToTask(requestArray[i]));
                    break;
                }
            }
        }
        return request;
    }

    private static Task mapToTask(String arg) {
        return new Task(arg);
    }
}
