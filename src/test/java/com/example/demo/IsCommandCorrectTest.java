package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IsCommandCorrectTest {
    @Test
    public void testIsCommandCorrect_exit() {
        String command = "exit";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_TASKS() {
        String command = "TASKS";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_CREATE_TASK() {
        String command = "USER1 CREATE_TASK DO_SOMETHING";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_CLOSE_TASK() {
        String command = "USER1 CLOSE_TASK DO_SOMETHING";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_REOPEN_TASK() {
        String command = "USER1 REOPEN_TASK DO_SOMETHING";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_DELETE_TASK() {
        String command = "USER1 DELETE_TASK DO_SOMETHING";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCommandCorrect_LIST_TASK() {
        String command = "USER1 LIST_TASK DO_SOMETHING";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandCorrect(command);
        assertEquals(expResult, result);
    }    
}
