package com.example.demo.model;

import com.example.demo.SwpContext;
import java.util.List;

public class ListsTasks extends SwpCommand {
    public ListsTasks(String userId) {
        super(userId);
    }

    @Override
    public Result execute(SwpContext context) {
        try {
            String tasksUserId = getArgs();
            List<Task> tasks = context.findByUser(tasksUserId);
            return new Result(ResultType.TASKS, tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ResultType.ERROR, null);
    }
}