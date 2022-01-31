package com.example.demo;

import java.io.IOException;

public class DemoApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Enter command");
        var server = new Server();
        server.start();

        var client = new Client();
        client.start();
    }
}
