package com.example.demo.service.facade;

import static com.example.demo.Utils.Utils.getBean;

import com.example.demo.service.commandService.CommandService;
import com.example.demo.service.parsing.Parser;
import com.example.demo.service.validator.Validator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class CommandFacadeImpl implements CommandFacade {

    Map<String, CommandService> serviceMap;
    private Validator validator;
    private Parser parser;

    public CommandFacadeImpl() {
        init();
    }

    private void init() {
        ServiceLoader<CommandService> serviceLoader = ServiceLoader.load(CommandService.class);
        for (CommandService command : serviceLoader) {
            this.serviceMap.put(command.getName(), command);
        }
        this.parser = getBean(ServiceLoader.load(Parser.class));
        this.validator = getBean(ServiceLoader.load(Validator.class));
    }

    public String executeCommand(String command){
        List<String> commandArray = parser.parseToCommand(command);
        validator.validate(commandArray, serviceMap);
        CommandService service = serviceMap.get(commandArray.get(1));
        service.validateAction(commandArray);
        return service.sendCommand(commandArray);
    }

}
