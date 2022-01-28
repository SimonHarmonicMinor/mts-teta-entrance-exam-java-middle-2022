package com.example.demo.service.validator;

import static java.util.Objects.nonNull;

import com.example.demo.exception.DemoException;
import com.example.demo.service.commandService.CommandService;
import com.example.demo.type.MyLittleBean;
import java.util.List;
import java.util.Map;

@MyLittleBean
public class ValidatorImpl implements Validator {

    @Override
    public void validateRequestFormat(List<String> commandArray,
            Map<String, CommandService> serviceMap) throws DemoException {
        if(commandArray.size() != 3) {
            throw new DemoException("Некорректный формат команды", "WRONG_FORMAT");
        }

        CommandService commandService = serviceMap.get(commandArray.get(1));
        if(!nonNull(commandService)) {
            throw new DemoException("Неизвестная команда", "ERROR");
        }

    }
}
