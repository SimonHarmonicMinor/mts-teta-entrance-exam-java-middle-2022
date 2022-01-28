package com.costa.socket.server.service;

import com.costa.socket.server.model.UserRequest;

/**
 * Check the condition, used by default at the beginning of handling
 * See {@link TaskHandler} {@link CommandHandler}
 */
public interface Validator {
    boolean validateComposition(String command);
    boolean validateAccess(UserRequest request);
}
