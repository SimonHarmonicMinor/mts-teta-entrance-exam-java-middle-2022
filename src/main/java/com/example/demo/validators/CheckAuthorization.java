package com.example.demo.validators;

import com.example.demo.database.IDatabase;
import com.example.demo.models.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CheckAuthorization {
    private static final Logger LOG = LoggerFactory.getLogger(CheckAuthorization.class);

    public boolean checkAuthorize(Request request, IDatabase db) {
        Set<String> users = db.getUsers();

        if (users.size() == 0) {
            LOG.debug("List authorized users is empty");
            return false;
        }

        if (!users.contains(request.getUser())) {
            LOG.debug("User " + request.getUser() + " does not exist");
            return false;
        }

        for (String user : users) {
            if (user.equals(request.getUser())) {
                LOG.debug("User " + user + " successful authorized");
                return true;
            }
        }

        LOG.debug("User " + request.getUser() + " not authorized");
        return false;
    }
}
