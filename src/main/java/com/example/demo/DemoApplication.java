package com.example.demo;


public class DemoApplication {
    private static Server server;

    public static void main(String[] args) throws Exception {
        server = new Server();
        server.start();
    }
}
