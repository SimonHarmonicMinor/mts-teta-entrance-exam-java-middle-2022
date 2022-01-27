package com.example.demo.validators;

import com.example.demo.models.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestValidatorImpl implements RequestValidator {
    private static final int memNum = 3;
    private static final Logger LOG = LoggerFactory.getLogger(RequestValidatorImpl.class);

    @Override
    public boolean isValid(String request) {
        String[] members = request.split(" ");

        if (memNum != members.length) {
            LOG.error("Invalid format request");
            return false;
        }
        try {
            Command.valueOf(members[1]);
        } catch (IllegalArgumentException e) {
            LOG.error("Unknown command");
            return false;
        }
        return true;
    }

    @Override
    public boolean isInvalid(String request) {
        return !isValid(request);
    }
}
