package mts.teta.entrance.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object of Task class is single task created by users. Has its own lifecycle. Most of time it is
 * located inside TasksPack class as a bundle of similar tasks created by variuos users.
 *
 * @author Yuri Mashkovtsev <yuri@mashkovtsev.ru>
 * @version 0.1
 */
class Task {

  private User owner;
  private String name;
  private String status;

  private static final Logger LOG = LoggerFactory.getLogger(Task.class);

  /**
   * Creates task and assigns it to given user. Also adds task to static TasksPack.pack.
   *
   * @param owner is user who creates this task
   * @param name is task name
   */
  Task(User owner, String name) {

    this.owner = owner;
    this.name = name;
    this.status = "CREATED";

    owner.addTask(this);
    TasksPack.add(this);

    LOG.info(
        "[+] Ok, new task '" + name + "' created, from now it owned by user " + owner.getName());
  }

  /**
   * Overloaded constructor for pseudo-task. We need it to indicate that there is no such task found
   * via static TasksPack.get() method.
   *
   * @return dummy object of Task class
   */
  Task() {

    this.name = "";
    // no need to initialize owner and status as well as renew TasksPack
  }

  /** @return task name */
  String getName() {

    return name;
  }

  /** @return task status ("CREATED", "DELETED" and so on) */
  String getStatus() {

    return status;
  }

  /**
   * Closes task belonging to user if fits required conditions.
   *
   * @param user is object of User class
   */
  void close(User user) throws AccessDeniedException, TaskStatusViolationException {

    if (!user.equals(owner)) {
      LOG.info(
          "[-] User "
              + user.getName()
              + " is not allowed to close tasks owned by user "
              + owner.getName());
      throw new AccessDeniedException("ACCESS_DENIED");
    }

    if (!"CREATED".equals(status)) {
      LOG.info("[-] Only tasks having CREATED status can be closed");
      throw new TaskStatusViolationException("ERROR");
    }

    LOG.info("[+] Ok, task '" + name + "' owned by user " + owner.getName() + " closed");

    status = "CLOSED";
  }

  /**
   * Deletes task belonging to user if fits required conditions.
   *
   * @param user is object of User class
   */
  void delete(User user) throws AccessDeniedException, TaskStatusViolationException {

    if (!user.equals(owner)) {
      LOG.info(
          "[-] User "
              + user.getName()
              + " is not allowed to delete tasks owned by user "
              + owner.getName());
      throw new AccessDeniedException("ACCESS_DENIED");
    }

    if (!"CLOSED".equals(status)) {
      LOG.info("[-] Only tasks having CLOSED status can be deleted");
      throw new TaskStatusViolationException("ERROR");
    }

    LOG.info("[+] Ok, task '" + name + "' owned by user " + owner.getName() + " deleted");

    status = "DELETED";
  }

  /**
   * Reopens task belonging to user if fits required conditions.
   *
   * @param user is object of User class
   */
  void reopen(User user) throws AccessDeniedException, TaskStatusViolationException {

    if (!user.equals(owner)) {
      LOG.info(
          "[-] User "
              + user.getName()
              + " is not allowed to reopen tasks owned by user "
              + owner.getName());
      throw new AccessDeniedException("ACCESS_DENIED");
    }

    if (!"CLOSED".equals(status)) {
      LOG.info("[-] Only tasks having CLOSED status can be reopened");
      throw new TaskStatusViolationException("ERROR");
    }

    LOG.info("[+] Ok, task '" + name + "' owned by user " + owner.getName() + " reopened");

    status = "CREATED";
  }
}

/**
 * Serves as custom user-defined exception for cases when user is not eligible to manipulate task
 */
class AccessDeniedException extends Exception {
  AccessDeniedException(String msg) {
    super(msg);
  }
}

/**
 * Serves as custom user-defined exception for cases when task status doesnt allow to manipulate
 * task
 */
class TaskStatusViolationException extends Exception {
  TaskStatusViolationException(String msg) {
    super(msg);
  }
}
