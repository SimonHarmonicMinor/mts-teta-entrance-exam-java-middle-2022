package com.example.demo;

import com.example.demo.impl.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    protected static CommandProcessor command;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        command = new CommandProcessor();

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
                        var result = command.processCommand(line);
                        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
                        serverWriter.write(result);
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