package com.example.demo.middleware;

import com.example.demo.Container;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.requests.Request;
import com.example.demo.responses.Response;
import com.example.demo.services.TaskService;

public class UserHandler extends Middleware {

    @Override
    public Response handle(Request request) throws Exception {
        try {
            Container.getInstance().setUser(request.getUser());
            TaskService.checkPermissions(request, new TaskRepository());
            return check(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), null);
        }
    }
}
