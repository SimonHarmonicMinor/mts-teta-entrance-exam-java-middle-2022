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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MyLittleBean
public class CommandFacadeImpl implements CommandFacade {

    private static final Logger LOG = LoggerFactory.getLogger(CommandFacadeImpl.class);
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
        try {
            //парсим команду
            List<String> commandArray = parser.parseToList(command);
            //проверяем формат
            validator.validateRequestFormat(commandArray, serviceMap);
            //определяем команду
            CommandService service = serviceMap.get(commandArray.get(1));
            //валидируем разрешения и прочие проверки
            service.validatePermission(commandArray.get(0), commandArray.get(2));
            //выполняем
            return service.sendCommand(commandArray.get(0), commandArray.get(2));
        }catch (DemoException e) {
            LOG.debug(e.getMessage());
            return e.getErrorResponse();
        }
    }

}
