package com.example.demo.validators;

import com.example.demo.enums.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckSyntax {

    private static final Logger LOG = LoggerFactory.getLogger(CheckSyntax.class);

    public boolean checkSyntax(String userCommand) {
        String[] userCommandArr = userCommand.split(" ");

        if (userCommandArr.length == 3) {
            LOG.debug("Checked, quantity word in command: " + userCommandArr.length);
        } else {
            LOG.debug("Unchecked, quantity word in command: " + userCommandArr.length);
            return false;
        }

        boolean checkCommand = false;
        for (Command command : Command.values()) {
            if (userCommandArr[1].equals(command.toString()))
                checkCommand = true;
        }

        if (checkCommand) {
            LOG.debug("Checked, command syntax right");
            return true;
        } else {
            LOG.debug("Unchecked, wrong format of command");
            return false;
        }
    }

}
