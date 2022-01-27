package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9090;
    private static Server server;
    private static Client client;

    @BeforeAll
    static void beforeAll() {
        server = new Server();
        client = new Client();
    }

    @Test
    void start() throws IOException {
        server.start(SERVER_PORT);
        client.start(SERVER_ADDRESS, SERVER_PORT);

        try {
            server.stop();
        } catch (Exception s){
            assertEquals(s.getMessage(), "Interrupted function call: accept failed");
        }
    }
}