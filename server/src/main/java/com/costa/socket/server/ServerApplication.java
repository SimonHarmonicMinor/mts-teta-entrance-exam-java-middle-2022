package com.costa.socket.server;

import com.costa.socket.server.dao.TaskDao;
import com.costa.socket.server.dao.UserDao;
import com.costa.socket.server.dao.impl.TaskDaoImpl;
import com.costa.socket.server.dao.impl.UserDaoImpl;
import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.daoservice.UserService;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.service.*;
import com.costa.socket.server.service.impl.BaseCommandHandler;
import com.costa.socket.server.service.impl.ServerRunner;
import com.costa.socket.server.service.impl.TaskHandlerFactory;
import com.costa.socket.server.service.impl.CommandValidator;
import com.costa.socket.server.daoservice.impl.TaskServiceImpl;
import com.costa.socket.server.daoservice.impl.UserServiceImpl;

public class ServerApplication {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        TaskDao taskDao = new TaskDaoImpl();
        TaskService taskService = new TaskServiceImpl(taskDao, userService);
        Validator validator = new CommandValidator(taskService);
        TaskHandlerFactory handlerFactory = new TaskHandlerFactory(validator, taskService);
        CommandHandler<ResultStatus> commandHandler = new BaseCommandHandler(validator, handlerFactory);

        new ServerRunner(commandHandler).start();
    }
}
