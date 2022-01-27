package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IHandler {

    Logger LOG = LoggerFactory.getLogger(IHandler.class);

    Response execute(Request request, IDatabase db);
}
