package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.demo.Configure.Params.DEFAULT_PORT;
import static com.example.demo.Configure.Params.DEFAULT_SOCKET_TIMEOUT;
import static java.lang.System.getProperty;

public class Configure {
    private static final Logger LOG = LoggerFactory.getLogger(Configure.class);

    private static final String applicationPort = "application.port";
    private static final String applicationSocketTimeout = "application.socket.timeout";

    public static Integer buildPort() {
        int port;

        if (getProperty(applicationPort) == null)
            port = buildPort(DEFAULT_PORT.getProp());
        else port = Integer.parseInt(getProperty(applicationPort));

        return port;
    }

    public static Integer buildSocketTimeout() {
        int timeout;

        if (getProperty(applicationSocketTimeout) == null)
            timeout = buildSocketTimeout(DEFAULT_SOCKET_TIMEOUT.getProp());
        else timeout = Integer.parseInt(getProperty(applicationSocketTimeout));

        return timeout;
    }

    private static Integer buildPort(String port) {
        LOG.debug("Build port " + port);
        System.setProperty(applicationPort, port);

        return Integer.valueOf(getProperty(applicationPort));
    }

    private static Integer buildSocketTimeout(String timeout) {
        LOG.debug("Build socket timeout " + timeout);
        System.setProperty(applicationSocketTimeout, timeout);

        return Integer.valueOf(getProperty(applicationSocketTimeout));
    }

    enum Params {
        DEFAULT_PORT("9090"),
        DEFAULT_SOCKET_TIMEOUT("90000");

        private final String prop;

        Params(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }
    }
}
