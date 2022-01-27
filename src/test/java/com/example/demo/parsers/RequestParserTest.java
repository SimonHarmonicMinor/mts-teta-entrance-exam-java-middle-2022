package com.example.demo.parsers;

import com.example.demo.enums.Command;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.models.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    @DisplayName("Один аргумент - WrongFormatException")
    void testOneArg() {
        assertThrows(WrongFormatException.class, ()-> RequestParser.parse("one"));
    }

    @Test
    @DisplayName("Более 3-х аргументов - WrongFormatException")
    void testMoreThanThreeArgs() {
        assertThrows(WrongFormatException.class, ()-> RequestParser.parse("one two three four"));
    }

    @Test
    @DisplayName("Неверная команда - WrongFormatException")
    void testIllegalCommand() {
        assertThrows(WrongFormatException.class, ()-> RequestParser.parse("one two three"));
    }

    @Test
    @DisplayName("Верная команда")
    void testOk() throws Exception{
        Request response = RequestParser.parse("VASYA CREATE_TASK CleanRoom");
        assertEquals("VASYA", response.getUser());
        assertEquals(Command.CREATE_TASK, response.getCommand());
        assertEquals("CleanRoom", response.getArg());
    }
}