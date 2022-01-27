package com.example.demo;

import com.example.demo.annotation.Route;
import com.example.demo.controller.base.ActionInterface;
import com.example.demo.controller.base.Controller;
import com.example.demo.entity.User;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.UserActionException;
import com.example.demo.protocol.Request;
import com.example.demo.protocol.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {

    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    private final Pattern requestPattern = Pattern.compile("^(?<user>\\w+)\\s+(?<command>[A-Z_]+)(\\s+(?<task>\\w+))?$");
    private final HashMap<String, ActionInterface> rule = new HashMap<>();
    private final Security security;

    public Router(Security security) {
        this.security = security;
    }

    public void attach(Controller controller) {
        for (Method action : controller.getClass().getMethods()) {
            if (action.isAnnotationPresent(Route.class)) {
                Route route = action.getAnnotation(Route.class);
                ActionInterface actionWrapper = request -> {
                    try {
                        return (Response) action.invoke(controller, request);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                };
                rule.put(route.path(), actionWrapper);
            }
        }
    }

    public Response handleRequest(String requestData) {
        Matcher matcher = requestPattern.matcher(requestData);
        if (!matcher.find()) {
            return Response.WRONG_FORMAT;
        }

        User user = security.authenticate(matcher.group("user"));
        Request request = new Request(user, matcher.group("command"), matcher.group("task"));
        if (rule.containsKey(request.getCommand())) {
            try {
                return rule.get(request.getCommand()).runAction(request);
            } catch (AccessDeniedException e) {
//                return Response.ACCESS_DENIED.setPayload(e.getMessage());
                return Response.ACCESS_DENIED;
            } catch (UserActionException e) {
                LOG.info("Has some wrong user action", e);
//                return Response.ERROR.setPayload(e.getMessage());
                return Response.ERROR;
            } catch (Throwable e) {
                LOG.error("Action wrapper failed", e);
            }
        } else {
            LOG.warn("Command not found " + request.getCommand());
        }

        return Response.ERROR;
    }
}
