package com.example.demo.validators;

import com.example.demo.database.IDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckAuthorization{
    private static final Logger LOG = LoggerFactory.getLogger(CheckAuthorization.class);

    public boolean checkAuthorize(String userCommand, IDatabase db){
        List<String> users = db.getUsers();

        if(users.size() == 0){
            LOG.debug("List authorized users is empty");
            return false;
        }

        String[] userCommandArr = userCommand.split(" ");

        for(String user: users){
            if(user.equals(userCommandArr[0])){
                LOG.debug("User " + user + " successful authorized");
                return true;
            }
        }

        LOG.debug("User " + userCommandArr[0] + " not authorized");
        return false;
    }
}
