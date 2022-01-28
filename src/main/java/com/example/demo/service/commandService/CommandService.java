package com.example.demo.service.commandService;

import com.example.demo.exception.DemoException;
import java.util.List;

public interface CommandService {

    void validatePermission(String userName, String arg) throws DemoException;
    String sendCommand(String userName, String arg);
    String getName();

}
