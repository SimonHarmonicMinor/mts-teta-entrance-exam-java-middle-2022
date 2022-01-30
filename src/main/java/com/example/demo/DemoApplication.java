package com.example.demo;

public class DemoApplication {

    static Server server = new Server();

    static Client client = new Client();

    public static void main(String[] args) throws Exception {

        server.start();
        client.startClientConnection();
        client.clientAction();
        client.startClientConnection();
        client.clientAction();
        server.stop();
    }

}
