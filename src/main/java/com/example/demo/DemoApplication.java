package com.example.demo;

import java.util.Scanner;

public class DemoApplication {
    public static void main(String[] args) throws Exception {
        Server server = new Server(new SwpContext());
        server.start();
        System.out.println("Type exit to shutdown");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            if (line.equals("exit")) {
                break;
            } else {
                System.out.println("Type exit to shutdown");
            }
        }
    }

}
