package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.Configure.buildPort;
import static com.example.demo.Configure.buildSocketTimeout;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigureTest {

    @Test
    @DisplayName("Проверка работы начальной конфигурации")
    void checkConfigure() {
        String applicationPort = System.getProperty("application.port");
        String applicationSocketTimeout = System.getProperty("application.socket.timeout");

        if (applicationPort == null)
            assertEquals(9090, buildPort()); //Configure.Params.DEFAULT_PORT
        else
            assertEquals(Integer.valueOf(applicationPort), buildPort());

        if (applicationSocketTimeout == null)
            assertEquals(90000, buildSocketTimeout()); //Configure.Params.DEFAULT_SOCKET_TIMEOUT
        else
            assertEquals(Integer.valueOf(applicationSocketTimeout), buildPort());
    }
}
