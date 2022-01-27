package com.example.demo.mappers;

import com.example.demo.models.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandStringToRequestMapperTest {

    @Test
    void map() {
        String user = "user";
        String command = "command";
        String task = "task";
        String userCommand = user + " " + command + " " + task;

        Request resultRequest = CommandStringToRequestMapper.map(userCommand);
        Request expectedRequest = new Request(user, command, task);

        assertEquals(expectedRequest.getUser(), resultRequest.getUser());
        assertEquals(expectedRequest.getCommand(), resultRequest.getCommand());
        assertEquals(expectedRequest.getArg(), resultRequest.getArg());
    }
}