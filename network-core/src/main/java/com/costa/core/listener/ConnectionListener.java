package com.costa.core.listener;

import com.costa.core.service.SocketConnection;

/**
 * Contract for standard features for work with {@link SocketConnection}
 */
public interface ConnectionListener {
    void onDisconnect(SocketConnection connection);
    void onException(SocketConnection connection, Exception e);
    void onReceiveMessage(SocketConnection connection, String message);
    void onConnectionSuccess(SocketConnection connection);
}
