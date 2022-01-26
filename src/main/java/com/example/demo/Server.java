package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static com.example.demo.Configure.buildPort;
import static com.example.demo.Configure.buildSocketTimeout;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    private ThreadServerSocket serverThread;

    public void start() throws IOException {
        serverSocket = new ServerSocket(buildPort(), -1, InetAddress.getLocalHost());
        serverSocket.setSoTimeout(buildSocketTimeout());
        serverThread = new ThreadServerSocket();
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() {
        serverThread.interrupt();
    }

    class ThreadServerSocket extends Thread {
        @Override
        public void interrupt() {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            } finally {
                super.interrupt();
            }
        }

        @Override
        public void run() {
            while (!serverSocket.isClosed()) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()));
                    ) {
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
                        serverWriter.write(line);
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    if (!(e instanceof SocketException && isInterrupted()))
                        LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        }

    }

}