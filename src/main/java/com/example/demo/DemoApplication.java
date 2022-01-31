package com.example.demo;


import com.example.demo.config.TaskConfig;
import com.example.demo.logic.TaskController;
import com.example.demo.logic.TaskResponser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplication {
    private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        String s1 = "VASYA CREATE_TASK CleanRoom";
        String s2 = "PETYA DELETE_TASK CleanRoom";
        String s3 = "PETYA CREATE_TASK Task1";
        String s4 = "PETYA CREATE_TASK Task2";
        String s5 = "VASYA LIST_TASK PETYA";
        String s6 = "VASYA CREATE_TASK CleanRoom";
        String s7 = "VASYA CREATE_TASK CleanRoom Cat";
        String s8 = "VASYA COMMAND CleanRoom";
        String s9 = "VASYA DELETE_TASK CleanRoom";


        TaskController controller = TaskConfig.createController();
        TaskResponser responser = TaskConfig.createResponser();

        responser.response(controller.handleRequest(s1));
        responser.response(controller.handleRequest(s2));
        responser.response(controller.handleRequest(s3));
        responser.response(controller.handleRequest(s4));
        responser.response(controller.handleRequest(s5));
        responser.response(controller.handleRequest(s6));
        responser.response(controller.handleRequest(s7));
        responser.response(controller.handleRequest(s8));
        responser.response(controller.handleRequest(s9));
    }
}
