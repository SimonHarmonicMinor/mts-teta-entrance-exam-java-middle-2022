package com.example.demo.services;

import com.example.demo.models.Response;

public interface RequestHandler {
    Response handle(String request);
}
