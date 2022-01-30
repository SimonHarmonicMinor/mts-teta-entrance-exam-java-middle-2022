package com.example.demo.middleware;

import com.example.demo.requests.Request;
import com.example.demo.responses.Response;

public abstract class Middleware {
    private Middleware next;

    public Middleware setNext(Middleware next) {
        this.next = next;
        return next;
    }

    public abstract Response handle(Request request) throws Exception;

    public Response check(Request request) throws Exception {
        if (this.next == null) {
            return new Response(null, null);
        }
        try {
            return this.next.handle(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), null);
        }
    }
}
