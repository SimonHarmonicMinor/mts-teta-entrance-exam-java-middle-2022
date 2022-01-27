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

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        command = new CommandProcessor();

        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()))
                    ) {
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        var result = command.processCommand(line);
                        LOG.info("result is: " + result);
                        serverWriter.write(result);
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}