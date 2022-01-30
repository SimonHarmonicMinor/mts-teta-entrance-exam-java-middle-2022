package mts.teta.entrance.exam;

// lets only import what we really need
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object of User class is single user. Most of time it is located inside UsersPack class as a
 * bundle of users.
 *
 * @author Yuri Mashkovtsev <yuri@mashkovtsev.ru>
 * @version 0.1
 */
class User {

  private String name;
  private Set<Task> tasks;

  private static final Logger LOG = LoggerFactory.getLogger(User.class);

  /**
   * Creates user and adds him/her to static UsersPack.pack.
   *
   * @param name is user's name
   */
  User(String name) {

    this.name = name;
    this.tasks = new HashSet<>();

    UsersPack.add(this);
  }

  /**
   * Overloaded constructor for pseudo-user. We need it to indicate that there is no such user found
   * via static UsersPack.get() method.
   *
   * @return dummy object of User class
   */
  User() {

    this.name = "";
    // no need to initialize tasks as well as renew UsersPack
  }

  /** @return user name */
  String getName() {

    return name;
  }

  /** @return set of tasks belonging to user */
  String getTasks() {

    Set<String> answer = new TreeSet<>();

    for (Task task : tasks) if (!"DELETED".equals(task.getStatus())) answer.add(task.getName());

    LOG.info("[+] Ok, list of tasks owned by user " + name + " provided");

    return "TASKS" + answer.toString();
  }

  /**
   * Adds task to set of tasks belonging to user
   *
   * @param task is object of Task class
   */
  void addTask(Task task) {

    tasks.add(task);
  }
}
