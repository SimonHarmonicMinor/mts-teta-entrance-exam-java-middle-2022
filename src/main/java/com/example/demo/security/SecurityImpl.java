package com.example.demo.security;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityImpl implements Security {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityImpl.class);

    @Override
    public boolean checkAccess(User user, Task task) {
        boolean ok = task.getUser().getName().equals(user.getName());

        if (ok) {
            LOG.info("Access complete for User: {}", user.getName());
            return true;
        }
        LOG.info("Access denied for User: {}", user.getName());
        return false;
    }
}
