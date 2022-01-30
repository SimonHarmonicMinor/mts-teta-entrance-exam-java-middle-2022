package com.example.demo;


import com.example.demo.middleware.Middleware;
import com.example.demo.middleware.RequestHandler;
import com.example.demo.middleware.RouterHandler;
import com.example.demo.middleware.UserHandler;

public class DemoApplication {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start(DemoApplication.init());
    }

    public static Middleware init() {
        Middleware middleware = new RequestHandler();
        middleware.setNext(new UserHandler())
                .setNext(new RouterHandler());
        return middleware;
    }

}
