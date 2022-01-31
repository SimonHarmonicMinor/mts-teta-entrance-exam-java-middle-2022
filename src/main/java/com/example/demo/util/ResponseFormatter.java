package com.example.demo.util;

import com.example.demo.model.Result;
import com.example.demo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ResponseFormatter {
    public static String createResponse(Result result) {
        return result.name();
    }

    public static String createResponse(List<Task> tasks) {
        List<String> names = new ArrayList<>();
        for (Task t : tasks) {
            names.add(t.getName());
        }

        return Result.TASKS.name() + " " + names.toString();
    }
}
