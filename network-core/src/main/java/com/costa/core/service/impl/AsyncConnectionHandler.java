package com.costa.core.service.impl;

import com.costa.core.service.ConnectionHandler;
import com.costa.core.service.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements connection processing via a listener
 * The connection provides handling in its own thread
 */
public class AsyncConnectionHandler implements ConnectionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncConnectionHandler.class);
    private final SocketConnection connection;
    private final Thread thread;

    public AsyncConnectionHandler(SocketConnection connection) {
        this.connection = connection;
        this.thread = new Thread(this::handle);
    }

    private void handle() {
        connection.getListener().onConnectionSuccess(connection);
        while (!Thread.interrupted()) {
            String message = connection.readMessage();
            connection.getListener().onReceiveMessage(connection, message);
        }
    }

    @Override
    public void startHandle() {
        LOG.debug("Start of handling for connection {}", connection);
        thread.start();
    }

    @Override
    public void stopHandle() {
        LOG.debug("Stop of handling for connection {}", connection);
        thread.interrupt();
    }
}
