package com.example.demo.service;

import com.example.demo.model.Response;

public interface Dispatcher {
    Response dispatch(String request);
}
