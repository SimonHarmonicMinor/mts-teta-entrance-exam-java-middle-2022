package com.garipov.socket.server.service;

import com.garipov.socket.server.dto.ServerResponse;

public interface CommandHandler {
    ServerResponse handle(String command);
}
