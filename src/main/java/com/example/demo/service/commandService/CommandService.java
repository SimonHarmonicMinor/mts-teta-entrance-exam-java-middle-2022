package com.example.demo.service.commandService;

import java.util.List;

public interface CommandService {

    void validateAction(List<String> commandArray);
    String sendCommand(List<String> commandArray);
    String getName();

}
