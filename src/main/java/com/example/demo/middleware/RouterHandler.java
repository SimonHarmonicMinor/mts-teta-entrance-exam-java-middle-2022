package com.example.demo.middleware;

import com.example.demo.Router;
import com.example.demo.controllers.Controller;
import com.example.demo.requests.Request;
import com.example.demo.responses.Response;

public class RouterHandler extends Middleware {
    @Override
    public Response handle(Request request) throws Exception {
        try {
            Router router = new Router(new Controller());
            return router.call(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), null);
        }
    }


}
