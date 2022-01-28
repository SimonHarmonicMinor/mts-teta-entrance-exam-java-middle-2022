package com.costa.socket.server.model;

/**
 * Available commands to be processed by the server
 */
public enum AvailableCommand {
    CREATE_TASK(Command.builder()
            .state(TaskState.CREATED)
            .resultStatus(ResultStatus.CREATED)
            .type(CommandType.MODIFY_TASK)
            .build()),
    DELETE_TASK(Command.builder()
            .state(TaskState.DELETED)
            .resultStatus(ResultStatus.DELETED)
            .type(CommandType.MODIFY_TASK)
            .build()),
    CLOSE_TASK(Command.builder()
            .state(TaskState.CLOSED)
            .resultStatus(ResultStatus.CLOSED)
            .type(CommandType.MODIFY_TASK)
            .build()),
    REOPEN_TASK(Command.builder()
            .state(TaskState.CREATED)
            .resultStatus(ResultStatus.REOPENED)
            .type(CommandType.MODIFY_TASK)
            .build()),
    LIST_TASK(Command.builder()
            .resultStatus(ResultStatus.TASKS)
            .type(CommandType.READ_TASK)
            .build());

    private final Command command;

    AvailableCommand(Command command) {
        this.command = command;
    }

    public Command getCommandModel() {
        return command;
    }
}
