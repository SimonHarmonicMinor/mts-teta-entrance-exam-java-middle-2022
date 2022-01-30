package com.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FindAndCloseOpenTaskTest {
    @BeforeAll
    public static void setUpClass() {
        DemoApplication.tasks[0][0] = "IGOR";
        DemoApplication.tasks[0][1] = "DO_SOMETHING";
        DemoApplication.tasks[0][2] = "CREATED";
        DemoApplication.tasks[1][0] = "VASYA";
        DemoApplication.tasks[1][1] = "CLEAN_GARDEN";
        DemoApplication.tasks[1][2] = "CREATED";
    }
    
    @Test
    public void testFindAndCloseOpenTask_AccessTrue_1() {

        String taskname = "DO_SOMETHING";
        String setStatus = "CLOSED";
        String[] arrCommand = {"IGOR", "CLOSE_TASK", "DO_SOMETHING"};
        String expResult = "CLOSED";
        DemoApplication.findAndCloseOpenTask(taskname, setStatus, arrCommand);
        String result = DemoApplication.access_result;
        assertEquals(expResult, result);
    }
    @Test
    public void testFindAndCloseOpenTask_AccessTrue_2() {
        DemoApplication.tasks[0][2] = "CLOSED";
        String taskname = "DO_SOMETHING";
        String setStatus = "DELETED";
        String[] arrCommand = {"IGOR", "DELETE_TASK", "DO_SOMETHING"};
        String expResult = "DELETED";
        DemoApplication.findAndCloseOpenTask(taskname, setStatus, arrCommand);
        String result = DemoApplication.access_result;
        assertEquals(expResult, result);
    }
    @Test
        public void testFindAndCloseOpenTask_AccessFalse() {
        String taskname = "DO_SOMETHING";
        String setStatus = "DELETED";
        String[] arrCommand = {"Vasya", "DELETE_TASK", "DO_SOMETHING"};
        String expResult = "ACCESS DENIED OR TASK NOT FOUND";
        DemoApplication.findAndCloseOpenTask(taskname, setStatus, arrCommand);
        String result = DemoApplication.access_result;
        assertEquals(expResult, result);
    }
    @Test
        public void testFindAndCloseOpenTask_WrongRequest_another_test() {
        String taskname = "DO_SOMETHING";
        String setStatus = "DELETED";
        String[] arrCommand = {"IGOR", "DELETE_TASK", "DO_SOMETHING"};
        String expResult = "WRONG REQUEST";
        DemoApplication.findAndCloseOpenTask(taskname, setStatus, arrCommand);
        String result = DemoApplication.access_result;
        assertEquals(expResult, result);
    }
}
