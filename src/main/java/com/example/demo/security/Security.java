package com.example.demo.security;

import com.example.demo.models.Task;
import com.example.demo.models.User;

public interface Security {
    boolean checkAccess(User user, Task task);
}
