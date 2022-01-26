package com.example.demo;

import com.example.demo.utils.handlers.ErrorHandler;
import com.example.demo.utils.handlers.RequestHandler;
import com.example.demo.repo.TaskRepo;
import com.example.demo.utils.Mapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AbstractServerTest {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Server server;

    @BeforeAll
    static void beforeAll() throws Exception {
        final RequestHandler requestHandler = new RequestHandler(new TaskRepo(), new Mapper(), new ErrorHandler());
        server = new Server(requestHandler);
        server.start();
    }

//    @BeforeEach
//    void beforeEach() throws Exception {
//        clientSocket = new Socket("localhost", 9090);
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//    }

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
        try {
            // more convenient for my tests
            clientSocket = new Socket("localhost", 9090);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(msg);
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
