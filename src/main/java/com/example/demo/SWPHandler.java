package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SWPHandler {

    private static final String CREATED = "CREATED";
    private static final String DELETED = "DELETED";
    private static final String CLOSED = "CLOSED";
    private static final String REOPENED = "REOPENED";
    private static final String TASKS = "TASKS";
    private static final String WRONG_FORMAT = "WRONG_FORMAT";
    private static final String ACCESS_DENIED = "ACCESS_DENIED";
    private static final String ERROR = "ERROR";

    private static final Map<String, Map<String, String>> userTasksMap = new HashMap<>();

    public String requestHandler(String request) {
        String response;
        if (isValidRequest(request)) {
            response = commandHandler(new Request(request));
        } else {
            response = WRONG_FORMAT;
        }
        return response;
    }

    private String commandHandler(Request request) {
        String response;

        switch (request.getCommand()) {
            case CREATE_TASK:
                response = createTask(request);
                break;
            case DELETE_TASK:
                response = deleteTask(request);
                break;
            case CLOSE_TASK:
                response = closeTask(request);
                break;
            case REOPEN_TASK:
                response = reopenedTask(request);
                break;
            case LIST_TASK:
                response = listTask(request);
                break;

            default:
                response = ERROR;
        }
        return response;
    }

    private String listTask(Request request) {
        String response;
        if (userTasksMap.containsKey(request.getArg())) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            String prefix = "";

            for (Map.Entry<String, String> entry : new TreeMap<>(userTasksMap.get(request.getArg())).entrySet()) {
                sb.append(prefix);
                prefix = ", ";
                sb.append(entry.getKey());
            }
            sb.append("]");
            response = TASKS + " " + sb;
        } else {
            response = ERROR;
        }
        return response;
    }

    private String closeTask(Request request) {
        String response;
        if (!Command.checkUniqTask(request.getArg())
                && userTasksMap.containsKey(request.getUserName())
                && userTasksMap.get(request.getUserName()).containsKey(request.getArg())) {
            userTasksMap.get(request.getUserName()).put(request.getArg(), CLOSED);
            response = CLOSED;
        } else {
            response = ERROR;
        }
        return response;
    }

    private String deleteTask(Request request) {
        String response;
        if (!Command.checkUniqTask(request.getArg())) {
            Map<String, String> tasks = userTasksMap.get(request.getUserName());
            if (tasks != null && tasks.containsKey(request.getArg())) {
                String statusTask = tasks.get(request.getArg());
                if (!statusTask.equals(CREATED)) {
                    tasks.remove(request.getArg());
                    response = DELETED;
                } else {
                    response = ERROR;
                }
            } else {
                response = ACCESS_DENIED;
            }

        } else {
            response = ERROR;
        }
        return response;
    }

    private String reopenedTask(Request request) {
        String response;
        if (!Command.checkUniqTask(request.getArg())
                && userTasksMap.containsKey(request.getUserName())) {

            Map<String, String> tasks = userTasksMap.get(request.getUserName());
            if (tasks.containsKey(request.getArg())) {
                String statusTask = tasks.get(request.getArg());
                if (statusTask.equals(CLOSED)) {
                    tasks.remove(request.getArg());
                    response = REOPENED;
                } else {
                    response = ERROR;
                }
            } else {
                response = ACCESS_DENIED;
            }

        } else {
            response = ERROR;
        }
        return response;
    }

    private String createTask(Request request) {
        String response;
        if (Command.checkUniqTaskAndAdd(request.getArg())) {
            userTasksMap.computeIfAbsent(request.getUserName(), k -> new HashMap<>())
                    .put(request.getArg(), CREATED);
            response = CREATED;
        } else {
            response = ERROR;
        }

        return response;
    }


    private boolean isValidRequest(String request) {
        String[] split = request.split(" ");
        return (split.length == 3 && Command.checkCommand(split[1]));
    }
}
