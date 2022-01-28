package com.example.demo;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DemoApplication {

    public static void main(String[] args) throws IOException {
//    Server server = new Server();
//    server.start();
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        String k = map.get("key");

        System.out.println("Hello, world");
    }

}
