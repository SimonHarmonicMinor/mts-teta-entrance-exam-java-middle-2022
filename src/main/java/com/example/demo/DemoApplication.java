package com.example.demo;


import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandValidator;
import com.example.demo.service.RequestHandler;

import java.io.IOException;

public class DemoApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, world");

        UserRepository userRepository = new UserRepository();
        TasksRepository tasksRepository = new TasksRepository();
        CommandValidator commandValidator = new CommandValidator(userRepository, tasksRepository);
        RequestHandler requestHandler = new RequestHandler(userRepository, tasksRepository, commandValidator);
        Server server = new Server(requestHandler);
        server.start();
    }

}
