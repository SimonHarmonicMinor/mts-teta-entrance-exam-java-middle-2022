package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

public class CloseHandler implements IHandler{

    @Override
    public Response execute(Request request, IDatabase db) {
        String userName = request.getUser();
        String taskName = request.getArg();

        if(db.closeTask(userName, taskName)){
            LOG.debug("Closed task " + taskName);
            return new Response(Result.CLOSED);
        }else {
            LOG.debug("Failed closed task " + taskName);
            return new Response(Result.ERROR);
        }
    }
}
