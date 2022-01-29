package com.example.demo.helper;


import com.example.demo.domain.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public Command stringToCommand(String stringToCommand){
        String[] commandParts = stringToCommand.split(" ");
        logger.debug("arg[0]=" + commandParts[0] + " arg[1]=" + commandParts[1] + " arg[2]=" + commandParts[2]);

        if (commandParts.length != 3)
            return new Command();

        return checkAction(commandParts[1]) ? new Command(commandParts[0], RequestAction.valueOf(commandParts[1]), commandParts[2]) : new Command();
    }

    private boolean checkAction(String action){
        try {
            RequestAction.valueOf(action);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }
}
