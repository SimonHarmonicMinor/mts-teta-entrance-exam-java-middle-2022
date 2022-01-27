package com.garipov.socket.server.service;

public interface CommandValidator {
    boolean validateComposition(String command);
    boolean validateAccess(String command);
}
