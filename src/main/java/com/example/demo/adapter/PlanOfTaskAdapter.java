package com.example.demo.adapter;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandler;
import com.example.demo.exception.FormatException;
import com.example.demo.service.PlanOfTask;

public class PlanOfTaskAdapter {

    private final PlanOfTask planOfTask;
    private final ExceptionHandler exceptionHandler;

    public PlanOfTaskAdapter(PlanOfTask planOfTask, ExceptionHandler exceptionHandler) {
        this.planOfTask = planOfTask;
        this.exceptionHandler = exceptionHandler;
    }

    public String execute(String line) {
        try {
            String[] arrayLine = line.split(" ");
            if (arrayLine.length != 3) {
                throw new FormatException("Неправильный формат команды");
            } else {
                Request request = new Request();
                request.setUserName(arrayLine[0]);
                request.setCommand(Command.valueOf(arrayLine[1]));
                request.setAdditionalParam(arrayLine[2]);
                return planOfTask.execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return exceptionHandler.handle(e);
        }
    }
}
