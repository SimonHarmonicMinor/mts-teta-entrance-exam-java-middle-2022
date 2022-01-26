package com.example.demo.utils.handlers;

import com.example.demo.enums.Result;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.exceptions.TaskNotBelongToUserException;
import com.example.demo.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    public Response handle(Throwable t) {
        Result result;
        if (t instanceof WrongFormatException) {
            LOG.error("Input format was wrong exception ", t);
            result = Result.WRONG_FORMAT;
        } else if (t instanceof TaskNotBelongToUserException) {
            LOG.error("Access to operation denied ", t);
            result = Result.ACCESS_DENIED;
        } else {
            LOG.error("Unexpected exception ", t);
            result = Result.ERROR;
        }
        return new Response(result);
    }
}
