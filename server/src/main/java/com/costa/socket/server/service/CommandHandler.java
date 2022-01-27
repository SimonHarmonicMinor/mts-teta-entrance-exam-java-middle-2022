package com.costa.socket.server.service;

import com.costa.socket.server.dto.ServerResponse;

public interface CommandHandler {
    ServerResponse handle(String command);
}
