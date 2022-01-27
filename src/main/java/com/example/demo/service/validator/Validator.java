package com.example.demo.service.validator;

import com.example.demo.service.commandService.CommandService;
import java.util.List;
import java.util.Map;

public interface Validator {

    void validate(List<String> commandArray,
            Map<String, CommandService> serviceMap);

}
