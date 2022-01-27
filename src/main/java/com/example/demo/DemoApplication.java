package com.example.demo;

import com.example.demo.controller.TaskController;

public class DemoApplication {

    public static void main(String[] args) {
        try {
            Server server = new Server(prelude());
            server.start();
        } catch (Exception e) {
            System.out.printf("Server down %s%n", e.getMessage());
        }
    }

    public static Router prelude()
    {
        Workflow workflow = new Workflow();
        Storage storage = new Storage();
        Security security = new Security(storage);

        Router router = new Router(security);
        router.attach(new TaskController(workflow, storage, security));

        return router;
    }
}
