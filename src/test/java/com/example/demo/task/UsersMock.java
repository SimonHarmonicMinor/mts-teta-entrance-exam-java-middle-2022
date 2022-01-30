package com.example.demo.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class UsersMock {
    public static final List<String> users = Arrays.asList("User1","User2","User3","User4","User5");

    public static String userFirst() {
        return users.get(0);
    }
}
