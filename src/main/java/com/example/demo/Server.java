package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final Router router;
    private ServerSocket serverSocket;

    public Server(Router router) {
        this.router = router;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()));
                    ) {
                        String line = serverReader.readLine();
//                        while (!(null == line || line.isEmpty())) {
                            LOG.debug("Request captured: " + line);
                            serverWriter.write(router.handleRequest(line).toString() + "\n");
                            serverWriter.flush();
//                        }
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
//        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}
