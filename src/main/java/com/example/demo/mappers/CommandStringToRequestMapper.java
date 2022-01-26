package com.example.demo.mappers;

import com.example.demo.models.Request;

public class CommandStringToRequestMapper {
    public static Request map(String userCommand){
        String[] userCommandArr = userCommand.split(" ");
        String userName = userCommandArr[0];
        String command = userCommandArr[1];
        String arg = userCommandArr[2];
        return new Request(userName, command, arg);
    }
}
