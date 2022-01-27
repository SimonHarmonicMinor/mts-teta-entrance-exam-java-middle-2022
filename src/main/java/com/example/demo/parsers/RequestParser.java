package com.example.demo.parsers;

import com.example.demo.enums.Command;
import com.example.demo.exceptions.DemoApplicationException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.models.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RequestParser {
    private static final Logger LOG = LoggerFactory.getLogger(RequestParser.class);

    public static Request parse(String rawRequest) throws DemoApplicationException {
        String[] splitted = rawRequest.trim().split("\\s+");
        if (splitted.length == 1 || splitted.length > 3) {
            LOG.error("Invalid request string: {}", rawRequest);
            throw new WrongFormatException();
        }
        if (Arrays.stream(Command.values()).noneMatch((t) -> t.name().equals(splitted[1]))) {
            LOG.error("Unknown command: {}", splitted[1]);
            throw new WrongFormatException();
        }
        return new Request(splitted[0], Command.valueOf(splitted[1]), splitted.length > 2 ? splitted[2] : null);
    }
}
