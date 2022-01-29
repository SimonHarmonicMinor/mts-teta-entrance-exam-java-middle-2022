package com.costa.core.service.impl;

import com.costa.core.service.SocketConnection;
import com.costa.core.service.ConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        try {
            connection.getListener().onConnectionSuccess(connection);
            while (!Thread.interrupted()) {
                String line = connection.getReader().readLine();
                if (line == null) return;
                connection.getListener().onReceiveMessage(connection, line);
            }
        } catch (IOException e) {
            LOG.error("An exception has occurred", e);
            connection.getListener().onException(connection, e);
            connection.disconnect();
        } finally {
            connection.getListener().onDisconnect(connection);
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
