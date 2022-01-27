package com.example.demo.controller.base;

import com.example.demo.protocol.Request;
import com.example.demo.protocol.Response;

public interface ActionInterface {
    Response runAction(Request request) throws Throwable;
}
