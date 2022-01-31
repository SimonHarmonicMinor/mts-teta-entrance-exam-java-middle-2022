package com.example.demo.service.handler;

import com.example.demo.model.Request;
import com.example.demo.model.Response;

public interface Handler {
    Response handle(String user, String arg);
    Request.Command getCommand();
}
