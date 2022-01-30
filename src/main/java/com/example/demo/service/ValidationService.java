package com.example.demo.service;

import com.example.demo.model.CommandType;

public class ValidationService {
    private ValidationService () {}

    public static void validateRequest(String[] request) throws Exception {
        if (request.length != 3) {
            throw new Exception("Invalid length");
        }
        CommandType.valueOf(request[1]);
    }
}
