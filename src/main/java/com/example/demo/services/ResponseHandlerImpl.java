package com.example.demo.services;

import com.example.demo.models.Response;
import com.example.demo.models.Result;

import java.util.List;

import static com.example.demo.models.Result.TASKS;

public class ResponseHandlerImpl implements ResponseHandler {
    @Override
    public String handle(Response response) {
        Result result = response.getResult();
        String responseString = result.toString();

        if (TASKS == result) {
            String argsList = generateArgsList(response.getArgs());
            responseString = responseString + " " + argsList;
        }
        return responseString;
    }

    private String generateArgsList(List<String> args) {
        if (args == null || args.isEmpty())
            return "[]";

        StringBuilder taskNamesList = new StringBuilder();
        taskNamesList.append("[");

        for (int i = 0; i < args.size(); i++) {
            taskNamesList.append(args.get(i));
            if (i == args.size() - 1)
                break;
            taskNamesList.append(", ");
        }
        taskNamesList.append("]");
        return taskNamesList.toString();
    }
}
