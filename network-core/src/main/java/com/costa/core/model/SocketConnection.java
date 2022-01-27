package com.costa.core.model;

import com.costa.core.listener.ConnectionListener;
import com.costa.core.service.ConnectionHandler;
import com.costa.core.service.impl.AsyncConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

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
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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

    public void start() {
        connectionHandler.startHandle();
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
}
