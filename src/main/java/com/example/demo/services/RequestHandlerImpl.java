package com.example.demo.services;

import com.example.demo.models.Command;
import com.example.demo.models.Response;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.validators.RequestValidator;

import java.util.Optional;

import static com.example.demo.models.Result.WRONG_FORMAT;

public class RequestHandlerImpl implements RequestHandler {
    private final UserRepository userRepository;
    private final RequestValidator requestValidator;
    private final CommandProcessor commandProcessor;

    public RequestHandlerImpl(UserRepository userRepository, RequestValidator requestValidator, CommandProcessor commandProcessor) {
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response handle(String request) {
        if (requestValidator.isInvalid(request))
            return new Response(WRONG_FORMAT);

        String[] requestMembers = request.split(" ");

        Command command = getCommandFromRequest(requestMembers);
        String userName = getUserNameFromRequest(requestMembers);
        String arg = getArgFromRequest(requestMembers);

        User user = getUser(userName);

        return commandProcessor.process(command, user, arg);
    }

    private Command getCommandFromRequest(String[] requestMembers) {
        return Command.valueOf(requestMembers[1]);
    }

    private String getUserNameFromRequest(String[] requestMembers) {
        return requestMembers[0];
    }

    private String getArgFromRequest(String[] requestMembers) {
        return requestMembers[2];
    }

    private User getUser(String userName) {
        User user;
        Optional<User> optionalUser = userRepository.findByName(userName);
        if (optionalUser.isPresent())
            user = optionalUser.get();
        else {
            user = new User(userName);
            userRepository.save(user);
        }
        return user;
    }
}
