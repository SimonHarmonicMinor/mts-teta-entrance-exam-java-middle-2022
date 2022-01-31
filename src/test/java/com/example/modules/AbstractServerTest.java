package com.example.modules;

import com.modules.Server;
import org.junit.jupiter.api.AfterAll;
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
        server = new Server();
        server.start();
        clientSocket = new Socket("localhost", 9090);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @AfterAll
    static void afterAll() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
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
