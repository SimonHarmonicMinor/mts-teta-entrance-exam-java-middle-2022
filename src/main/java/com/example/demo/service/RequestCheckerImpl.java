package com.example.demo.service;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Формирования листа проверок для запросов
 */

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
