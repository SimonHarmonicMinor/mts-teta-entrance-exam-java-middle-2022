package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractServerTest {

    private static Server server;

    @BeforeAll
    static void beforeAll() throws Exception {
        server = new Server(DemoApplication.prelude());
        server.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        server.stop();
    }

    protected String sendMessage(String msg) {

        try {
            Socket clientSocket = new Socket("localhost", 9090);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println(msg);
            // socket closed by server
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
