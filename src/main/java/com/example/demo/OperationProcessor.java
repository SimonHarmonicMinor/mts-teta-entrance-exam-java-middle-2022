package com.example.demo;

import com.example.demo.domain.Service;
import com.example.demo.domain.Store;
import com.example.demo.proto.Operation;
import com.example.demo.proto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
public class OperationProcessor {
    private final int EXACT_PARAMS_COUNT = 3;
    private final Service service;
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public OperationProcessor(Store store) {
        this.service = new Service(store);
    }

    private Object getResponse(final String inputLine) {
        try {
            assertBool(inputLine != null && !inputLine.isEmpty(), "empty input");

            var params = inputLine.split(" ");
            assertBool(params.length == EXACT_PARAMS_COUNT, "wrong params count");

            var user = params[0];
            var operation = Operation.valueOf(params[1]);
            var arg = params[2];

            switch (operation) {
                case CREATE_TASK:
                    service.createTask(user, arg);
                    return Response.CREATED;
                case CLOSE_TASK:
                    service.closeTask(user, arg);
                    return Response.CLOSED;
                case REOPEN_TASK:
                    service.reopenTask(user, arg);
                    return Response.REOPENED;
                case DELETE_TASK:
                    service.deleteTask(user, arg);
                    return Response.DELETED;
                case LIST_TASK:
                    var tasks = service.listTasks(user, arg);
                    return builcTaskList(tasks);
                default:
                    return Response.ERROR;
            }
        } catch (WrongFormat e) {
            logException(e);
            return Response.WRONG_FORMAT;
        } catch (Service.AccessDenied e) {
            logException(e);
            return Response.ACCESS_DENIED;
        } catch (Exception e) {
            logException(e);
            return Response.ERROR;
        }
    }

    private void logException(Exception e) {
        LOG.debug("Exception occured: " + e.getMessage());
    }

    public String processLine(final String inputLine) {
        LOG.debug("server processor input: " + inputLine);
        var response = getResponse(inputLine).toString();
        LOG.debug("server processor output: " + response);
        return response;
    }

    private static void assertBool(boolean cond, String message) throws WrongFormat {
        if (!cond) {
            throw new WrongFormat(message);
        }
    }

    private static class WrongFormat extends RuntimeException {
        public WrongFormat(String message) {
            super(message);
        }
    }

    public static String builcTaskList(Collection<String> tasks) {
        return tasks
            .stream()
            .collect(Collectors.joining(", ", "[", "]"));
    }


}
