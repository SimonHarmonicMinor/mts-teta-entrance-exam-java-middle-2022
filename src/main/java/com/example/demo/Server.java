package com.example.demo;

import com.example.demo.models.Response;
import com.example.demo.services.RequestHandler;
import com.example.demo.services.RequestHandlerImpl;
import com.example.demo.services.ResponseHandler;
import com.example.demo.services.ResponseHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
            requestHandler = new RequestHandlerImpl();
            responseHandler = new ResponseHandlerImpl();
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
                        line = line.trim();
                        LOG.debug("Request captured: {}", line);

                        Response response = requestHandler.handle(line);
                        String responseString = responseHandler.handle(response);
                        LOG.debug("Response: {}", responseString);

                        serverWriter.write(responseString);
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