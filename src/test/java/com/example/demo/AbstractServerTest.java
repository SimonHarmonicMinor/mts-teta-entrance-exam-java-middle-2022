package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
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
        User vasya = new User("VASYA");
        User petya = new User("PETYA");
        ArrayList<User> users = new ArrayList<>();
        users.add(vasya);
        users.add(petya);
        UserRepository userRepository = new UserRepository(users);
        server = new Server(userRepository);
        server.start();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        clientSocket = new Socket("localhost", 9090);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @AfterEach
    void afterEach() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    @AfterAll
    static void afterAll() throws Exception {
        server.stop();
    }

    protected String sendMessage(String msg) {
        out.println(msg);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
