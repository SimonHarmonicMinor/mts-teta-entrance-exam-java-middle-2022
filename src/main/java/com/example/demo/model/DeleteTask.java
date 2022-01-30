package com.example.demo.model;

import com.example.demo.SwpContext;
import java.util.Objects;

public class DeleteTask extends SwpCommand {
    public DeleteTask(String userId) {
        super(userId);
    }

    @Override
    public Result execute(SwpContext context) {
        String taskName = getArgs();
        Task task = context.findByName(taskName);
        if (task != null) {
            if (Objects.equals(getUserId(), task.getCreator())
                    && task.getState().equals(Task.TaskState.CLOSED)) {
                context.deleteTask(taskName);
                return new Result(ResultType.DELETED, null);
            } else {
                return new Result(ResultType.ACCESS_DENIED, null);
            }
        } else {
            return new Result(ResultType.ERROR, null);
        }
    }
}
