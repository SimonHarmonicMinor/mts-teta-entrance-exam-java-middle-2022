package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.TasksRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.*;
import com.example.demo.service.handler.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractServerTest {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Server server;

    @BeforeAll
    static void beforeAll() throws Exception {
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
        server = new Server(dispatcher);
        server.start();
    }

//    @BeforeEach
    void beforeEach() throws Exception {
        clientSocket = new Socket("localhost", 9090);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

//    @AfterEach
    void afterEach() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    @AfterAll
    static void afterAll() throws Exception {
        server.stop();
    }

    protected String sendMessage(String msg) throws Exception {
        beforeEach();
        out.println(msg);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            afterEach();
        }
    }
}
