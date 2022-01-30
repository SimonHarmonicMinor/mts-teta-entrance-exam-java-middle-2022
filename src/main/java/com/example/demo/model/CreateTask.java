package com.example.demo.model;

import com.example.demo.SwpContext;
import java.util.Objects;

public class CreateTask extends SwpCommand {
    public CreateTask(String userId) {
        super(userId);
    }

    @Override
    public Result execute(SwpContext context) {
        String taskName = getArgs();
        Task task = context.findByName(taskName);
        if (task == null) {
            context.addTask(new Task(taskName, getUserId()));
            return new Result(ResultType.CREATED, null);
        } else {
            if (Objects.equals(task.getCreator(), getUserId())) {
                return new Result(ResultType.ERROR, null);
            } else {
                return new Result(ResultType.ACCESS_DENIED, null);
            }
        }
    }
}