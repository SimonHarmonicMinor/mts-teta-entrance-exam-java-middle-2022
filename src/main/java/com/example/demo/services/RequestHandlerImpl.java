package com.example.demo.services;

import com.example.demo.models.Command;
import com.example.demo.models.Response;
import com.example.demo.models.User;
import com.example.demo.validators.RequestValidator;
import com.example.demo.validators.RequestValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.demo.models.Result.WRONG_FORMAT;

public class RequestHandlerImpl implements RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandlerImpl.class);
    private final RequestValidator requestValidator = new RequestValidatorImpl();
    private final CommandProcessor commandProcessor = new CommandProcessorImpl();

    @Override
    public Response handle(String request) {
        if (requestValidator.isInvalid(request))
            return new Response(WRONG_FORMAT);

        String[] requestMembers = request.split(" ");

        Command command = getCommandFromRequest(requestMembers);
        User user = getUserFromRequest(requestMembers);
        String arg = getArgFromRequest(requestMembers);

        return commandProcessor.process(command, user, arg);
    }

    private Command getCommandFromRequest(String[] requestMembers) {
        return Command.valueOf(requestMembers[1]);
    }

    private User getUserFromRequest(String[] requestMembers) {
        return new User(requestMembers[0]);
    }

    private String getArgFromRequest(String[] requestMembers) {
        return requestMembers[2];
    }
}
