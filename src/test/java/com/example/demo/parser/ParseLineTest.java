package com.example.demo.parser;

import com.example.demo.entity.RequestEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseLineTest {

    ParseLine parseLine = new ParseLine();

    @Test
    void getResultTest() {
        String request = "VASYA CREATE TEST";

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUserName("VASYA");
        requestEntity.setCommand("CREATE");
        requestEntity.setName("TEST");

        assertEquals(requestEntity, parseLine.getResult(request));
    }
}