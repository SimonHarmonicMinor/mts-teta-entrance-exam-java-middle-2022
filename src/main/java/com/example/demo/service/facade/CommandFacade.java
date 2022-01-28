package com.example.demo.service.facade;

import com.example.demo.exception.DemoException;

public interface CommandFacade {

    String sendCommand(String request) throws DemoException;

}
