package com.example.demo.core.factories;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.data.Response;
import com.example.demo.core.data.TasksRepository;
import com.example.demo.core.tools.Validator;

public abstract class Action {
    public TasksRepository tasksRepository = new TasksRepository();
    public Validator validator = new Validator();
    public abstract Response execute(CommandLine commandLine);
}
