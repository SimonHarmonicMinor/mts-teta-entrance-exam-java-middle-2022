package com.example.demo.utils;

import com.example.demo.enums.Command;
import com.example.demo.model.Request;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    private final Mapper mapper = new Mapper();
    private final String INPUT_ONE = "USER CREATE_TASK ARRRRG";
    private final String INPUT_TWO = "VASYA DELETE_TASK ARG AND SOME OTHER STUFF";
    private final String INPUT_THREE = "lowerCaseTest REOPEN_TASK arg and justice";
    private final String INPUT_FOUR = "lowerCaseTest LIST_TASK";

    @Test
    void pattern_test() {
        Pattern p = Pattern.compile("^[^\\s]+ [^\\s]+$");
        assertFalse(p.matcher(INPUT_ONE).find());
        assertFalse(p.matcher(INPUT_TWO).find());
        assertFalse(p.matcher(INPUT_THREE).find());
        assertTrue(p.matcher(INPUT_FOUR).find());
    }

    @Test
    void has_arg_all_ok() {
        assertTrue(mapper.hasArg(INPUT_ONE));
        assertTrue(mapper.hasArg(INPUT_TWO));
        assertTrue(mapper.hasArg(INPUT_THREE));
        assertFalse(mapper.hasArg(INPUT_FOUR));
    }

    @Test
    void has_no_arg_all_ok() {
        assertFalse(mapper.hasNoArg(INPUT_ONE));
        assertFalse(mapper.hasNoArg(INPUT_TWO));
        assertFalse(mapper.hasNoArg(INPUT_THREE));
        assertTrue(mapper.hasNoArg(INPUT_FOUR));
    }

    @Test
    void map_must_be_ok() {
        Request r1 = mapper.inputToRequest(INPUT_ONE);
        assertEquals("USER", r1.getUser());
        assertEquals(Command.CREATE_TASK, r1.getCommand());
        assertEquals("ARRRRG", r1.getArg());

        Request r2 = mapper.inputToRequest(INPUT_TWO);
        assertEquals("VASYA", r2.getUser());
        assertEquals(Command.DELETE_TASK, r2.getCommand());
        assertEquals("ARG AND SOME OTHER STUFF", r2.getArg());

        Request r3 = mapper.inputToRequest(INPUT_THREE);
        assertEquals("lowerCaseTest", r3.getUser());
        assertEquals(Command.REOPEN_TASK, r3.getCommand());
        assertEquals("arg and justice", r3.getArg());

        Request r4 = mapper.inputToRequest(INPUT_FOUR);
        assertEquals("lowerCaseTest", r4.getUser());
        assertEquals(Command.LIST_TASK, r4.getCommand());
    }
}