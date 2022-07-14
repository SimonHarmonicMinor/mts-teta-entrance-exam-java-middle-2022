package com.example.demo;

import com.example.demo.enums.ECommand;
import com.example.demo.enums.ETaskStatus;
import com.example.models.Request;
import com.example.models.Response;
import com.example.models.Task;
import com.example.models.User;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.demo.enums.EAnswer.*;
import static com.example.demo.enums.ECommand.*;

public class TaskManagerImpl implements TaskManagerRepository {

    private final Map<String, List<Task>> userMap = new HashMap<>();

    public Response call(String req) {
        if (WRONG_FORMAT.name().equals(req)) {
            return new Response(WRONG_FORMAT);
        }

        Request request = stringToRequest(req);

        switch (request.getCommand()) {
            case __DELETE_ALL:
                deleteAllUserTasks();
                return new Response(DELETED);
            case CREATE_TASK:
                return createTask(request);
            case CLOSE_TASK:
                return changeTaskStatus(request, ETaskStatus.CLOSED);
            case REOPEN_TASK:
                return changeTaskStatus(request, ETaskStatus.CREATED);
            case DELETE_TASK:
                return deleteTask(request);
            case LIST_TASK:
                return getListTask(request);
            default:
                return new Response(ERROR);
        }
    }

    @Override
    public Response changeTaskStatus(Request request, ETaskStatus taskStatus) {
        if (!userMap.isEmpty()) {
            boolean isExistsUser = userMap.keySet().stream()
                    .anyMatch(i -> i.equals(request.getUserName()));

            if (isExistsUser) {
                for (Map.Entry<String, List<Task>> map : userMap.entrySet()) {
                    if (map.getKey().equals(request.getUserName())) {
                        Optional<Task> optionalTask = map.getValue().stream()
                                .filter(i -> i.getName().equals(request.getArg()))
                                .findFirst();

                        if (optionalTask.isPresent()) {
                            Task task = optionalTask.get();

                            if (taskStatus == ETaskStatus.CREATED && task.getStatus().equals(ETaskStatus.CREATED)) {
                                return new Response(ERROR);
                            }

                            task.setStatus(taskStatus);

                            return new Response(taskStatus == ETaskStatus.CREATED ? REOPENED.name() : taskStatus.name());
                        }
                    }
                }
            } else {
                return new Response(ACCESS_DENIED);
            }
        }

        return new Response(ERROR);
    }

    @Override
    public Response deleteTask(Request request) {
        if (!userMap.isEmpty()) {
            if (userMap.containsKey(request.getUserName())) {
                for (Map.Entry<String, List<Task>> map : userMap.entrySet()) {
                    if (map.getKey().equals(request.getUserName())) {
                        map.getValue().removeIf(i -> i.getName().equals(request.getArg()) && i.getStatus().equals(ETaskStatus.CLOSED));

                        if (map.getValue().stream().noneMatch(i -> i.getName().equals(request.getArg()))) {
                            return new Response(DELETED);
                        }
                    }
                }
            } else {
                return new Response(ACCESS_DENIED);
            }
        }

        return new Response(ERROR);
    }

    @Override
    public Response createTask(Request request) {
        List<Task> tasks = new ArrayList<>();

        if (!userMap.isEmpty()) {
            if (userMap.containsKey(request.getUserName())) {
                for (Map.Entry<String, List<Task>> map : userMap.entrySet()) {
                    if (map.getKey().equals(request.getUserName())) {
                        if (map.getValue().stream().noneMatch(i -> i.getName().equals(request.getArg()))) {
                            map.getValue().add(new Task(request.getArg()));

                            return new Response(CREATED);
                        }
                    }
                }
            } else {
                return addNewTask(tasks, request);
            }
        } else {
            return addNewTask(tasks, request);
        }

        return new Response(ERROR);
    }

    @Override
    public Response getListTask(Request request) {
        List<String> taskNames = request.getArg() != null
                ? getTaskNames(request.getArg())
                : getTaskNames(request.getUserName());

        return new Response(String.format("%s %s", TASKS.name(), taskNames));
    }

    public String validateRequest(String request) {
        if (__DELETE_ALL.name().equals(request)) {
            return __DELETE_ALL.name();
        }

        String[] partsOfRequest = request.split(" ");

        if (LIST_TASK.name().equals(partsOfRequest[0])) {
            if (partsOfRequest.length < 2) {
                return WRONG_FORMAT.name();
            }

            return request;
        } else if (partsOfRequest.length != 3) {
            return WRONG_FORMAT.name();
        } else if (partsOfRequest[0].isEmpty() || partsOfRequest[1].isEmpty() || partsOfRequest[2].isEmpty()) {
            return WRONG_FORMAT.name();
        }

        boolean isValidCommand = Arrays.stream(ECommand.values())
                .anyMatch(i -> i.name().equals(partsOfRequest[1]));

        if (!isValidCommand) {
            return WRONG_FORMAT.name();
        }

        return request;
    }

    public Request stringToRequest(String requestStr) {
        Request request = new Request();
        String[] partsOfRequest = requestStr.split(" ");

        if (LIST_TASK.name().equals(partsOfRequest[0])) {
            request.setUserName(partsOfRequest[1]);
            request.setCommand(ECommand.valueOf(partsOfRequest[0]));

            return request;
        } else if (__DELETE_ALL.name().equals(partsOfRequest[0])) {
            request.setCommand(ECommand.valueOf(partsOfRequest[0]));

            return request;
        }

        request.setUserName(partsOfRequest[0]);
        request.setCommand(ECommand.valueOf(partsOfRequest[1]));
        request.setArg(partsOfRequest[2]);

        return request;
    }

    @Override
    public void deleteAllUserTasks() {
        userMap.clear();
    }

    @Override
    public Response addNewTask(List<Task> tasks, Request request) {
        tasks.add(new Task(request.getArg()));
        userMap.put(request.getUserName(), tasks);

        return new Response(CREATED);
    }

    private List<String> getTaskNames(String user) {
        return userMap.entrySet().stream()
                .filter(i -> user.equals(i.getKey()))
                .flatMap(i -> i.getValue().stream())
                .filter(i -> i.getStatus().equals(ETaskStatus.CREATED) || i.getStatus().equals(ETaskStatus.CLOSED))
                .map(Task::getName)
                .collect(Collectors.toList());
    }
}
