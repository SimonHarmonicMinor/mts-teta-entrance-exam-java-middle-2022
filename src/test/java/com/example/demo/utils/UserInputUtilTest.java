package com.example.demo.utils;

import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.service.dto.CommandProcessingServiceDto;
import org.junit.jupiter.api.Test;

import static com.example.demo.enums.ServerCommand.CREATE_TASK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserInputUtilTest {

    @Test
    void emptyInputThrowExceptionTest() {
        assertThrows(WrongFormatException.class,
                () -> UserInputUtil.searchAndValidateServerCommandFromUserInput(""));
    }

    @Test
    void tooManyCommandThrowExceptionTest() {
        assertThrows(WrongFormatException.class,
                () -> UserInputUtil.searchAndValidateServerCommandFromUserInput("USER CREATE_TASK DELETE_TASK"));
    }

    @Test
    void tooFewArgumentsThrowExceptionTest() {
        assertThrows(WrongFormatException.class,
                () -> UserInputUtil.searchAndValidateServerCommandFromUserInput("CREATE_TASK Task1"));
    }

    @Test
    void noAnyCommandThrowExceptionTest() {
        assertThrows(WrongFormatException.class,
                () -> UserInputUtil.searchAndValidateServerCommandFromUserInput("USER CREATE Task1"));
    }

    @Test
    void wrongCommandRegisterThrowExceptionTest() {
        assertThrows(WrongFormatException.class,
                () -> UserInputUtil.searchAndValidateServerCommandFromUserInput("USER create_task Task1"));
    }

    @Test
    void rightCommandTest() {
        CommandProcessingServiceDto dto = UserInputUtil.searchAndValidateServerCommandFromUserInput("USER CREATE_TASK Task1");
        assertEquals(dto.getServerCommand(), CREATE_TASK);
        assertEquals(dto.getSplitInput()[0], "USER");
        assertEquals(dto.getSplitInput()[2], "Task1");
    }

}