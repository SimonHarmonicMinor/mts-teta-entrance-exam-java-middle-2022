package com.costa.socket.server.service;

import com.costa.socket.server.dto.ServerResponse;
import com.costa.socket.server.model.CommandType;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.dto.UserRequest;
import com.costa.socket.server.service.impl.TaskHandlerFactory;

/**
 * The task handler, by default, the separation is done through the {@link CommandType}
 * using the factory {@link TaskHandlerFactory}
 */
public interface TaskHandler {
    ServerResponse<ResultStatus> handle(UserRequest request);
}
