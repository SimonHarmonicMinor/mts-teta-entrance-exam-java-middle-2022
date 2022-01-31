package com.example.demo.parser;

import com.example.demo.entity.RequestEntity;
import com.example.demo.exception.WrongFormatException;

public class ParseLine {

    public RequestEntity getResult(String line) {

        String[] parseRequest = line.split(" ");
        if (parseRequest.length != 3) {
            throw new WrongFormatException();
        }

        RequestEntity request = new RequestEntity();
        request.setUserName(parseRequest[0]);
        request.setCommand(parseRequest[1]);
        request.setName(parseRequest[2]);

        return request;
    }
}
