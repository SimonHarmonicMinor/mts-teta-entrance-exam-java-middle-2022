package com.example.demo;


import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.TasksRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.*;
import com.example.demo.service.handler.*;

import java.io.IOException;
import java.util.List;

public class DemoApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, world");

        UserRepository userRepository = new UserRepositoryImpl();
        TasksRepository tasksRepository = new TasksRepositoryImpl();
        Validator validator = new ValidatorImpl(userRepository, tasksRepository);
        List<Handler> handlers = List.of(
                new CreateHandler(userRepository, tasksRepository, validator),
                new CloseHandler(userRepository, tasksRepository, validator),
                new ReopenHandler(userRepository, tasksRepository, validator),
                new DeleteHandler(userRepository, tasksRepository, validator),
                new ListHandler(userRepository, tasksRepository, validator));
        Dispatcher dispatcher = new DispatcherImpl(handlers);
        Server server = new Server(dispatcher);
        server.start();
    }

}
