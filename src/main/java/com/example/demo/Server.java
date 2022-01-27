package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static final SWPHandler swpHandler = new SWPHandler();

    private ServerSocket serverSocket;

    public void start() throws IOException {
        LOG.info("start");
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                        String request = in.readLine();
                        LOG.info(request);
                        String response = swpHandler.requestHandler(request);
                        LOG.info(response);
                        serverWriter.write(response);
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

    public void stop() throws IOException {
        serverSocket.close();
    }
}