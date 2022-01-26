package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

public class DeleteHandler implements IHandler{
    @Override
    public Response execute(Request request, IDatabase db) {
        String userName = request.getUser();
        String taskName = request.getArg();

        if(db.deleteTask(userName, taskName)){
            LOG.debug("Deleted task " + taskName);
            return new Response(Result.DELETED);
        }else{
            LOG.debug("Failed delete task " + taskName);
            return new Response(Result.ERROR);
        }
    }
}
