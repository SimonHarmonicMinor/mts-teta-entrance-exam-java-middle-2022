package com.example.demo.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckSyntaxTest {

    private final CheckSyntax checkSyntax = new CheckSyntax();

    @Test
    void checkSyntax() {
        String userCommandIncorrectCountWord = "user task";
        String userCommandIncorrectCommand = "user CREATE task";
        String userCommandCorrect = "user CREATE_TASK task";

        assertFalse(checkSyntax.checkSyntax(userCommandIncorrectCountWord));
        assertFalse(checkSyntax.checkSyntax(userCommandIncorrectCommand));
        assertTrue(checkSyntax.checkSyntax(userCommandCorrect));
    }
}