package com.example.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static com.example.demo.Configure.buildPort;

public class AbstractServerTest {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Server server;
    private static int port;

    @BeforeAll
    static void beforeAll() throws Exception {
        server = new Server();
        server.start();
        port = buildPort();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
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
    static void afterAll() {
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
