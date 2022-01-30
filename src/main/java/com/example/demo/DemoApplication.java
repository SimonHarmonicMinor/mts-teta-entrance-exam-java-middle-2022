package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class DemoApplication {
  private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

  public static void main(String[] args) throws IOException {
    logger.debug("Start.");

    logger.debug("End");
  }

}
