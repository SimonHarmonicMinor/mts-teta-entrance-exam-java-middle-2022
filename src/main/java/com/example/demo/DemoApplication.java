package com.example.demo;


import com.modules.Client;
import com.modules.Server;

/**
 * Демо для показа работы сервера
 * stop - выход из приложения
 */
public class DemoApplication {

    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client("localhost", 9090);
        try {
            server.start();
            client.run();
        } catch (Exception e) {
            try {
                server.stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Приложение запущено, введите команду:");
    }
}
