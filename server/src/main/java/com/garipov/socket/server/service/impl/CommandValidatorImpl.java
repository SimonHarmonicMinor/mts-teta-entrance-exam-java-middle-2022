package com.garipov.socket.server.service.impl;

import com.garipov.socket.server.service.CommandValidator;

import java.util.regex.Pattern;

public class CommandValidatorImpl implements CommandValidator {
    @Override
    public boolean validateComposition(String command) {
        Pattern pattern = Pattern.compile("\\w+\\s\\w+\\s\\w+");
        return pattern.matcher(command).matches();
    }

    @Override
    public boolean validateAccess(String command) {
        return false;
    }
}
