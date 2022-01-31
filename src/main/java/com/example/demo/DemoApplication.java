package com.example.demo;


import com.modules.Client;
import com.modules.Server;

/**
 * Демо для показа работы сервера
 * stop - выход из приложения
 */
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Client client = new Client("localhost", 9090);
        server.start();
        client.run();
        System.out.println("Приложение запущено, введите команду:");
    }
}
