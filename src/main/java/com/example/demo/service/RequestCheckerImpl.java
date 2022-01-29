package com.example.demo.service;

import com.example.demo.entity.Request;

import java.util.List;

public class RequestCheckerImpl implements RequestChecker {

    private final List<RequestChecker> requestCheckerList;

    public RequestCheckerImpl(List<RequestChecker> requestCheckerList) {
        this.requestCheckerList = requestCheckerList;
    }

    @Override
    public void check(Request request) {
        requestCheckerList.forEach(requestChecker -> requestChecker.check(request));

    }
}
