package com.example.demo.utils.handlers;

import com.example.demo.enums.Result;
import com.example.demo.exceptions.TaskNotBelongToUserException;
import com.example.demo.exceptions.TaskNotExistException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.model.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {
    private static final ErrorHandler handler = new ErrorHandler();
    @Test
    void handle_method_test_all_match() {
        final Response wrongFormat = handler.handle(new WrongFormatException("bad input"));
        assertEquals(Result.WRONG_FORMAT.name(), wrongFormat.getAsString());

        final Response accessDenied = handler.handle(new TaskNotBelongToUserException("operation access denied"));
        assertEquals(Result.ACCESS_DENIED.name(), accessDenied.getAsString());

        final Response otherError = handler.handle(new TaskNotExistException("not exist"));
        assertEquals(Result.ERROR.name(), otherError.getAsString());
    }
}