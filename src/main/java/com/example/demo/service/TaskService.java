package com.example.demo.service;

import com.example.demo.domain.Task;
import com.example.demo.helper.Answer;

import java.util.List;

public interface TaskService {

    Answer save(String taskName, String userName);

    Answer close(String taskName, String userName);

    Answer delete(String taskName, String userName);

    Answer reopen(String taskName, String userName);

    List<Task> selectAll(String name);
}
