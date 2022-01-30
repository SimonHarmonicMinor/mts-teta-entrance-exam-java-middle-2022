package com.example.demo.request;

/**
 * Create finally string of response.
 *
 * Create by fkshistero on 30.01.2022.
 */
public class ResponseBuilder {
    private static final String startResponse = "RESULT";

    public static String createResponse(Response response){
        return String.format("%s %s", startResponse, response.name());
    }

    public static String createResponse(Response response, String arg){
        return String.format("%s %s [%s]", startResponse, response, arg);
    }
}
