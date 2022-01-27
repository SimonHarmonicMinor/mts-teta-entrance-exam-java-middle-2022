package com.example.demo.service;

import com.example.demo.entity.Request;

import java.util.List;

public class RequestCheckerImpl implements RequestChecker {

    private List<RequestChecker> requestCheckerList;

    @Override
    public void check(Request request) {
        requestCheckerList.forEach(requestChecker -> requestChecker.check(request));

    }
}
