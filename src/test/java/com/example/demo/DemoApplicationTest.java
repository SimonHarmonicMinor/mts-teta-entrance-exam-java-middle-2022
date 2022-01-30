package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DemoApplicationTest {
/*    
    public DemoApplicationTest() {
    }
  
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

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
    
    @Test
    public void testIsCommandOrderCorrect_1() {
        DemoApplication.tasks[0][0] = "IGOR";
        DemoApplication.tasks[0][1] = "DO_SOMETHING";
        DemoApplication.tasks[0][2] = "CREATED";
        String commandUser = "IGOR";
        String commandTaskName = "DO_SOMETHING";
        String commandArg = "CLOSE_TASK";
        boolean expResult = true;
        boolean result = DemoApplication.isCommandOrderCorrect(commandUser, commandTaskName, commandArg);
        assertEquals(expResult, result);

    }

    /*
    @Test
    public void testGetFreeRow() {
        System.out.println("getFreeRow");
        int expResult = 0;
        int result = DemoApplication.getFreeRow();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testShowTaskList() {
        System.out.println("showTaskList");
        String[] arrCommand = null;
        DemoApplication.showTaskList(arrCommand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testAddTask() {
        System.out.println("addTask");
        String username = "";
        String taskname = "";
        DemoApplication.addTask(username, taskname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindAndCloseOpenTask() {
        System.out.println("findAndCloseOpenTask");
        String taskname = "";
        String setStatus = "";
        String[] arrCommand = null;
        DemoApplication.findAndCloseOpenTask(taskname, setStatus, arrCommand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testAction() {
        System.out.println("action");
        String sendedCommand = "";
        String[] arrCommand = null;
        DemoApplication.action(sendedCommand, arrCommand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsTasksEmpty() {
        System.out.println("isTasksEmpty");
        boolean expResult = false;
        boolean result = DemoApplication.isTasksEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testShowTasksTable() {
        System.out.println("showTasksTable");
        DemoApplication.showTasksTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsTaskExist() {
        System.out.println("isTaskExist");
        String taskname = "";
        boolean expResult = false;
        boolean result = DemoApplication.isTaskExist(taskname);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testCommandExecute() {
        System.out.println("commandExecute");
        String command = "";
        DemoApplication.commandExecute(command);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        DemoApplication.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
