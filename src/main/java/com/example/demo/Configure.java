package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.demo.Configure.Params.DEFAULT_PORT;
import static java.lang.System.getProperty;

public class Configure {
    private static final Logger LOG = LoggerFactory.getLogger(Configure.class);

    private static final String applicationPort = "application.port";

    public static Integer buildPort() {
        if (getProperty(applicationPort) == null)
            buildPort(DEFAULT_PORT.getProp());

        return Integer.valueOf(getProperty(applicationPort));
    }

    public static Integer buildPort(String port) {
        LOG.debug("Build port " + port);
        System.setProperty(applicationPort, port);

        return Integer.valueOf(getProperty(applicationPort));
    }

    enum Params {
        DEFAULT_PORT("9090");

        private final String prop;

        Params(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }
    }
}
