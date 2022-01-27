package com.example.demo.repository;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;

public interface PlanOfTask {

    Result execute(Request request);

}
