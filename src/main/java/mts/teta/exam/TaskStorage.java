package mts.teta.exam;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

class TaskStorage {
    private final List<Task> tasks = new LinkedList<>();

    String getTaskOwner(String taskName) {
        return tasks.stream().filter(t -> t.name.equals(taskName)).findFirst().map(t -> t.user).orElse(null);
    }

    void checkCommandAccessRights(Command command) throws ErrorResponseException {
        switch (command.getType()) {

            case CREATE_TASK:
                break;
            case CLOSE_TASK:
            case REOPEN_TASK:
            case DELETE_TASK:
                var task= getNonDeletedTask(command.getArgument());
                if (!task.user.equals(command.getUser())) {
                    throw new InvalidAccessRightsException();
                }
                break;
            case LIST_TASK:
                break;
        }
    }

    void executeCommand(Command command) throws TaskStorageException {
        ResultType result;
        switch (command.getType()) {
            case CREATE_TASK:
                createTask(command.getUser(), command.getArgument());
                command.setResult(ResultType.CREATED);
                break;
            case CLOSE_TASK:
                closeTask(command.getArgument());
                command.setResult(ResultType.CLOSED);
                break;
            case REOPEN_TASK:
                reopenTask(command.getArgument());
                command.setResult(ResultType.REOPENED);
                break;
            case DELETE_TASK:
                deleteTask(command.getArgument());
                command.setResult(ResultType.DELETED);
                break;
            case LIST_TASK:
                var userName=command.getArgument()==null?command.getUser(): command.getArgument();
                var tasks= getUserTasks(userName);
                command.setResult(ResultType.TASKS);
                command.setResultArgument("["+String.join(",",tasks)+"]");
                break;
        }
    }

    private List<String> getUserTasks(String userName)
    {
        return tasks.stream().filter(t -> t.user.equals(userName) && t.status != TaskStatus.DELETED).map(t -> t.name).collect(toList());
    }

    private Task getTask(String taskName) throws TaskNotFoundException {
        var task=tasks.stream().filter(t -> t.name.equalsIgnoreCase(taskName)).findFirst();
        if (task.isEmpty())
        {
            throw new TaskNotFoundException();
        }
        else
        {
            return task.get();
        }
    }

    private Task getNonDeletedTask(String taskName) throws TaskNotFoundException {
        var task=tasks.stream().filter(t -> t.name.equalsIgnoreCase(taskName) && t.status!=TaskStatus.DELETED).findFirst();
        if (task.isEmpty())
        {
            throw new TaskNotFoundException();
        }
        else
        {
            return task.get();
        }
    }

    private void createTask(String userName, String taskName) throws TaskAlreadyExistsException {
        if (tasks.stream().anyMatch(t -> t.name.equalsIgnoreCase(taskName) && t.status != TaskStatus.DELETED)) {
            throw new TaskAlreadyExistsException();
        }
        else
        {
            tasks.add(new Task(){{user=userName;name=taskName;status=TaskStatus.CREATED;}});
        }
    }

    private void closeTask(String taskName) throws TaskStorageException {
        var task= getNonDeletedTask(taskName);
        if (task.status != TaskStatus.CREATED)
        {
            throw new InvalidTaskStateException();
        }
        task.status=TaskStatus.CLOSED;
    }

    private void reopenTask(String taskName) throws TaskStorageException {
        var task= getNonDeletedTask(taskName);
        if (task.status != TaskStatus.CLOSED)
        {
            throw new InvalidTaskStateException();
        }
        task.status=TaskStatus.CREATED;
    }

    private void deleteTask(String taskName) throws TaskStorageException {
        var task= getNonDeletedTask(taskName);
        if (task.status != TaskStatus.CLOSED)
        {
            throw new InvalidTaskStateException();
        }
        task.status=TaskStatus.DELETED;
    }
}
