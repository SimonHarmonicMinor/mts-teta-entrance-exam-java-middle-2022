package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String EXIT_COMMAND = "exit";

    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private Socket clientSocket;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    private BufferedReader consoleReader;

    public void start(String serverAddress, int serverPort) {
        Thread clientThread = new Thread(() -> {

            String clientCommand = "";
            while (!clientCommand.equals(EXIT_COMMAND)) {
                try {
                    clientSocket = new Socket(serverAddress, serverPort);

                    clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                    consoleReader = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("Please enter your command: ");
                    clientCommand = consoleReader.readLine();

                    clientWriter.println(clientCommand);

                    String serverResponse = clientReader.readLine();
                    System.out.println(serverResponse);

                } catch (Exception e) {
                    LOG.error("Error when connect to server ", e);
                    stop();
                    break;
                }
            }

            stop();
        });
        clientThread.setDaemon(false);
        clientThread.start();
    }

    public void stop() {
        try {
            consoleReader.close();
            clientWriter.close();
            clientReader.close();
            clientSocket.close();
        } catch (IOException io) {
            LOG.error("Error when close connection ", io);
        }
    }

}
