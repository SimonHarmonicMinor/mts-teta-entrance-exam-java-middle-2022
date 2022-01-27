package com.example.demo.services;

import com.example.demo.database.IDatabase;
import com.example.demo.database.InMemoryDatabase;
import com.example.demo.enums.Command;
import com.example.demo.enums.Result;
import com.example.demo.handlers.*;
import com.example.demo.mappers.CommandStringToRequestMapper;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import com.example.demo.validators.CheckAuthorization;
import com.example.demo.validators.CheckSyntax;

public class CommandService {
    private final IDatabase db;

    private final CheckAuthorization checkAuthorization;
    private final CheckSyntax checkSyntax;

    private final IHandler createHandler;
    private final IHandler closeHandler;
    private final IHandler reopenHandler;
    private final IHandler deleteHandler;
    private final IHandler taskListHandler;

    public CommandService() {
        db = new InMemoryDatabase();
        checkSyntax = new CheckSyntax();
        checkAuthorization = new CheckAuthorization();
        createHandler = new CreateHandler();
        closeHandler = new CloseHandler();
        reopenHandler = new ReopenHandler();
        deleteHandler = new DeleteHandler();
        taskListHandler = new TaskListHandler();
    }

    public Response execute(String userCommand) {

        if (!checkSyntax.checkSyntax(userCommand)) {
            return new Response(Result.WRONG_FORMAT);
        }

        Request request = CommandStringToRequestMapper.map(userCommand);
        Command command = Command.valueOf(request.getCommand());

        switch (command) {
            case CREATE_TASK:
                return createHandler.execute(request, db);

            case CLOSE_TASK:
                return checkAuthorization.checkAuthorize(request, db) ?
                        closeHandler.execute(request, db) :
                        new Response(Result.ACCESS_DENIED);

            case REOPEN_TASK:
                return checkAuthorization.checkAuthorize(request, db) ?
                        reopenHandler.execute(request, db) :
                        new Response(Result.ACCESS_DENIED);

            case DELETE_TASK:
                return checkAuthorization.checkAuthorize(request, db) ?
                        deleteHandler.execute(request, db) :
                        new Response(Result.ACCESS_DENIED);

            case LIST_TASK:
                return taskListHandler.execute(request, db);

            default:
                return new Response(Result.ERROR);
        }
    }

}
