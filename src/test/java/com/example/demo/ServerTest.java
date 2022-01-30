package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

    @Test
    void testWrongFormat() throws Exception  {
        String response = sendMessage("request");
        assertEquals("WRONG_FORMAT", response);
    }
}