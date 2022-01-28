package com.costa.socket.server.service;

import com.costa.socket.server.dto.ServerResponse;

/**
 * User command handler
 */
public interface CommandHandler<T> {
    ServerResponse<T> handle(String command);
}
