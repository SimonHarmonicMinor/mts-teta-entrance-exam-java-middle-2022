package com.example.demo;


import java.io.IOException;

public class DemoApplication {

    public static void main(final String[] args) throws IOException {
        final Server server = new Server();
        server.start();
    }
}
