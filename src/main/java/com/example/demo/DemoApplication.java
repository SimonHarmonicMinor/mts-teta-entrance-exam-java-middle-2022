package com.example.demo;


public class DemoApplication {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();

        server.stop();
    }
}
