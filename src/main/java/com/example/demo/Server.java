package com.example.demo;

import com.example.demo.impl.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    private Command command;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        LOG.info("started");
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    BufferedReader serverReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    Writer serverWriter = new BufferedWriter(
                            new OutputStreamWriter(connection.getOutputStream()));
                    try {
                        String line = serverReader.readLine();
                        LOG.info("Request captured: " + line);
                        command = new Command(line);
                        LOG.info("user is: " + command.getUser());

                        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
                        serverWriter.write(line);
                        serverWriter.flush();
                    } catch (Exception e) {
                        serverWriter.write(e.getMessage());
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}