package com.example.demo.factory;

import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.TaskRepositoryImpl;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserRepositoryImpl;
import com.example.demo.security.Security;
import com.example.demo.security.SecurityImpl;
import com.example.demo.services.*;
import com.example.demo.validators.RequestValidator;
import com.example.demo.validators.RequestValidatorImpl;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    private static final TaskRepository taskRepository;
    private static final UserRepository userRepository;
    private static final Security security;
    private static final RequestValidator validator;
    private static final ResponseHandler responseHandler;
    private static final CommandProcessor commandProcessor;
    private static final RequestHandler requestHandler;

    private static final Map<Class<?>, Object> context;

    static {
        taskRepository = new TaskRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        security = new SecurityImpl();
        validator = new RequestValidatorImpl();
        responseHandler = new ResponseHandlerImpl();
        commandProcessor = new CommandProcessorImpl(taskRepository, security);
        requestHandler = new RequestHandlerImpl(userRepository, validator, commandProcessor);

        context = new HashMap<>();

        context.put(TaskRepository.class, taskRepository);
        context.put(UserRepository.class, userRepository);
        context.put(Security.class, security);
        context.put(RequestValidator.class, validator);
        context.put(ResponseHandler.class, responseHandler);
        context.put(CommandProcessor.class, commandProcessor);
        context.put(RequestHandler.class, requestHandler);
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz.cast(context.get(clazz));
    }
}
