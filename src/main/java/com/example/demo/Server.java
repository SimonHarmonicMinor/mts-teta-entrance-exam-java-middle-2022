package com.example.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.example.demo.handlers.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    private RequestHandler handler;

    public Server(RequestHandler handler) {
        this.handler = handler;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))
                    ) {
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        serverWriter.write(handler.handle(line));
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

    @Override
    public void close() throws IOException {
        stop();
    }
}