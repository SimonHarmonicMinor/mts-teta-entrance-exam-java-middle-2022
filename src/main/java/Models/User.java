package Models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {

    String name;
    Set<String> tasks;

    User(String name) {
        tasks = new HashSet<String>();
        this.name = name;
    }

    public Set<String> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(String task_name) {
        tasks.add(task_name);
    }

    public static User getUser(Map<String, User> users, String user_name) {
        User user = users.get(user_name);
        if (user == null) {
            user = createUser(users, user_name);
        }
        return user;
    }

    public static User createUser(Map<String, User> users, String user_name) {
        User user = new User(user_name);
        users.put(user_name, user);
        return user;
    }
}
