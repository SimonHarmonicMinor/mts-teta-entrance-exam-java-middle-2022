package com.costa.core.service;

import com.costa.core.listener.ConnectionListener;
import com.costa.core.service.impl.AsyncConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Connection class for multi-user client-server applications via {@link java.net.Socket}
 */
public class SocketConnection {
    private static final Logger LOG = LoggerFactory.getLogger(SocketConnection.class);
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final ConnectionListener listener;
    private final Socket socket;
    private final ConnectionHandler connectionHandler;

    public SocketConnection(ConnectionListener listener, String ip, int port) throws IOException {
        this(listener, new Socket(ip, port));
    }

    public SocketConnection(ConnectionListener listener, Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.listener = listener;
        this.socket = socket;
        this.connectionHandler = new AsyncConnectionHandler(this);
    }

    public void sendMessage(String message) {
        LOG.trace("Connection: {} \n Sending message: {}", this, message);
        try {
            writer.write(message + "\r\n");
            writer.flush();
        } catch (IOException e) {
            listener.onException(this, e);
            disconnect();
        }
    }

    public String readMessage() {
        try {
            String line = reader.readLine();
            if (line == null)
                throw new IOException("Received incorrect message");

            LOG.trace("Connection: {} \n Reading message: {}", this, line);
            return line;
        } catch (IOException e) {
            listener.onException(this, e);
            disconnect();
        }

        return null;
    }

    public void start() {
        connectionHandler.startHandle();
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public ConnectionListener getListener() {
        return listener;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return String.format("Connection %s:%s", socket.getInetAddress(), socket.getPort());
    }

    public void disconnect() {
        connectionHandler.stopHandle();
        try {
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            listener.onException(this, e);
        }
        listener.onDisconnect(this);
    }
}
