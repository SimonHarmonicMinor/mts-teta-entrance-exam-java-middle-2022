package com.example.demo.model;

import com.example.demo.SwpContext;
import java.util.Objects;

public class ReOpenTask extends SwpCommand {
    public ReOpenTask(String userId) {
        super(userId);
    }

    @Override
    public Result execute(SwpContext context) {
        try {
            String taskName = getArgs();
            Task task = context.findByName(taskName);
            if (task != null) {
                if (Objects.equals(getUserId(), task.getCreator())) {
                    task.reOpen();
                    return new Result(ResultType.REOPENED, null);
                } else {
                    return new Result(ResultType.ACCESS_DENIED, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ResultType.ERROR, null);
    }
}
