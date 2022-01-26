package com.example.demo.utils;

import com.example.demo.enums.Command;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.model.Request;

import java.util.regex.Pattern;

public class Mapper {
    private static final Pattern REQUEST_WITH_ARG_FORMAT = Pattern.compile("([^\\s]+) ([\\w]+) ([\\w]*)$");
    private static final Pattern REQUEST_WITH_NO_ARG_FORMAT = Pattern.compile("^[^\\s]+ [^\\s]+$");

    public boolean hasNoArg(String input) {
        return REQUEST_WITH_NO_ARG_FORMAT.matcher(input).find();
    }

    public boolean hasArg(String input) {
        return REQUEST_WITH_ARG_FORMAT.matcher(input).find();
    }

    public Request inputToRequest(String input) {
        try {
            input = input.trim();
            String[] tokens = input.split(" ");
            String user = tokens[0];
            String command = tokens[1];
            String arg = tokens.length > 2 ? tokens[2] : null;
            return new Request(user, Command.valueOf(command), arg);
        } catch (Exception e) {
            throw new WrongFormatException("bad input: " + input);
        }
    }
}
