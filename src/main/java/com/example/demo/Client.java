package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9090;
    private static final String EXIT_COMMAND = "exit";

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private Socket clientSocket;

    public void start() {
        Thread clientThread = new Thread(() -> {

            while (true) {
                try {
                    clientSocket = new Socket(SERVER_IP, SERVER_PORT);

                    BufferedReader clientReader = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter clientWriter = new PrintWriter(
                            clientSocket.getOutputStream(), true);
                    BufferedReader consoleReader = new BufferedReader(
                            new InputStreamReader(System.in));

                        System.out.println("Please enter your command: ");
                        String clientCommand = consoleReader.readLine();

                        clientWriter.println(clientCommand);

                        String serverResponse = clientReader.readLine();
                        System.out.println(serverResponse);

                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        clientThread.setDaemon(false);
        clientThread.start();
    }

    public void stop() throws Exception {
        clientSocket.close();
    }

}
