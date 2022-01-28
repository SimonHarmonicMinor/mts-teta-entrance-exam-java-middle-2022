package com.example.demo.service.facade;

import static com.example.demo.Utils.Utils.getBean;

import com.example.demo.exception.DemoException;
import com.example.demo.service.commandService.CommandService;
import com.example.demo.service.parsing.Parser;
import com.example.demo.service.validator.Validator;
import com.example.demo.type.MyLittleBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

@MyLittleBean
public class CommandFacadeImpl implements CommandFacade {

    Map<String, CommandService> serviceMap;
    private Validator validator;
    private Parser parser;

    public CommandFacadeImpl() {
        init();
    }

    private void init() {
        this.serviceMap = new HashMap<>();
        ServiceLoader<CommandService> serviceLoader = ServiceLoader.load(CommandService.class);
        for (CommandService command : serviceLoader) {
            this.serviceMap.put(command.getName(), command);
        }
        this.parser = getBean(ServiceLoader.load(Parser.class));
        this.validator = getBean(ServiceLoader.load(Validator.class));
    }

    @Override
    public String executeCommand(String command) throws DemoException {

        List<String> commandArray = parser.parseToList(command);
        validator.validateRequestFormat(commandArray, serviceMap);
        CommandService service = serviceMap.get(commandArray.get(1));
        service.validatePermission(commandArray.get(0), commandArray.get(2));
        return service.sendCommand(commandArray.get(0), commandArray.get(2));
    }

}
