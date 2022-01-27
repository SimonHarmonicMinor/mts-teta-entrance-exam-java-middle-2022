package com.example.demo;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.TransitionNotAllowedException;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.WorkflowException;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Workflow {

    private static final Logger LOG = LoggerFactory.getLogger(Workflow.class);
    private static final ArrayList<Task.State[]> transitions = new ArrayList<>(List.of(
            new Task.State[]{Task.State.CREATED, Task.State.CLOSED},
            new Task.State[]{Task.State.CLOSED, Task.State.CREATED},
            new Task.State[]{Task.State.CLOSED, Task.State.DELETED}
    ));

    public void apply(User user, Task task, Task.State targetState) throws WorkflowException {
        if (!task.getUser().equals(user)) {
            throw new AccessDeniedException(task.getUser(), user);
        }

        Optional<Task.State[]> transitionOpt = Workflow.transitions.stream()
                .filter(t -> t[0].equals(task.getState()) && t[1].equals(targetState)).findAny();
        if (transitionOpt.isEmpty()) {
            throw new TransitionNotAllowedException(
                String.format("Have no transition form \"%s\" to \"%s\" state",
                    task.getState().toString(),
                    targetState.toString()
                )
            );
        }
        task.setState(transitionOpt.get()[1]);
        LOG.info(
                String.format("Task \"%s\" changed state to \"%s\" by \"%s\"",
                task.getTitle(),
                task.getState().toString(),
                task.getUser().getName()
            )
        );
    }
}
