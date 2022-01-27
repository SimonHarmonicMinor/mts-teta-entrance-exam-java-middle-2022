package com.costa.core.config;

/**
 * Base properties for network config
 */
public class BaseNetworkConfig {
    private static String host;
    private static int port;

    private BaseNetworkConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        BaseNetworkConfig.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        BaseNetworkConfig.port = port;
    }
}
