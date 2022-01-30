package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IsCommandOrderCorrectTest {
    @BeforeAll
    public static void setUpClass() {
        DemoApplication.tasks[0][0] = "IGOR";
        DemoApplication.tasks[0][1] = "DO_SOMETHING";
        DemoApplication.tasks[0][2] = "CREATED";
    }
    @Test
    public void testIsCommandOrderCorrect_CreatedToClose() {
        String commandUser = "IGOR";
        String commandTaskName = "DO_SOMETHING";
        String commandArg = "CLOSE_TASK";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandOrderCorrect(commandUser, commandTaskName, commandArg);
        assertEquals(expResult, result);
    }
    @Test
    public void testIsCommandOrderCorrect_CreatedToDelete() {
        String commandUser = "IGOR";
        String commandTaskName = "DO_SOMETHING";
        String commandArg = "DELETE_TASK";
        boolean expResult = false;
        boolean result = DemoApplication.isCommandOrderCorrect(commandUser, commandTaskName, commandArg);
        assertEquals(expResult, result);

    } 
    @Test
    public void testIsCommandOrderCorrect_CloseToDelete() {
        DemoApplication.tasks[0][2] = "CLOSED";
        String commandUser = "IGOR";
        String commandTaskName = "DO_SOMETHING";
        String commandArg = "DELETE_TASK";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandOrderCorrect(commandUser, commandTaskName, commandArg);
        assertEquals(expResult, result);
    }
    @Test
    public void testIsCommandOrderCorrect_DeleteToClose() {
        DemoApplication.tasks[0][2] = "DELETED";
        String commandUser = "IGOR";
        String commandTaskName = "DO_SOMETHING";
        String commandArg = "REOPED_TASK";
        boolean expResult = false;
        boolean result = DemoApplication.isCommandOrderCorrect(commandUser, commandTaskName, commandArg);
        assertEquals(expResult, result);
    } 
}
