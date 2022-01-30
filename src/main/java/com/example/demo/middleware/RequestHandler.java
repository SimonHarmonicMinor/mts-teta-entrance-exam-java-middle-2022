package com.example.demo.middleware;

import com.example.demo.requests.Request;
import com.example.demo.responses.Response;

public class RequestHandler extends Middleware {

    @Override
    public Response handle(Request request) throws Exception {
        try {
            request.prepare();
            return check(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), null);
        }
    }
}
