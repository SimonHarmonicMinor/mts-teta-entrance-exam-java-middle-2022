package com.costa.socket.server.model;

/**
 * The entity describes the state of the command
 * For example, has a {@link CommandType} to get the desired handler,
 * an expected {@link TaskState} in the repository, an expected {@link ResultStatus} for a response
 */
public class Command {
    private final CommandType type;
    private final TaskState state;
    private final ResultStatus resultStatus;

    public Command(CommandType type, TaskState state, ResultStatus resultStatus) {
        this.type = type;
        this.state = state;
        this.resultStatus = resultStatus;
    }

    public CommandType getType() {
        return type;
    }

    public TaskState getState() {
        return state;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {
        private CommandType type;
        private TaskState state;
        private ResultStatus resultStatus;

        public CommandBuilder type(CommandType type){
            this.type = type;
            return this;
        }

        public CommandBuilder state(TaskState state){
            this.state = state;
            return this;
        }

        public CommandBuilder resultStatus(ResultStatus status){
            this.resultStatus = status;
            return this;
        }

        public Command build(){
            return new Command(type, state, resultStatus);
        }
    }
}
