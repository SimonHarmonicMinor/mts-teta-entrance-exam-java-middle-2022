package mts.teta.entrance.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object of Request class contains what server gets from client. Request can be be wrong, it gets
 * analyzed in constructor then SecurityException is raised for empty or broken requests as well as
 * for request containing surprising commands. If preliminary checks passed, request goes further
 * via process() method.
 *
 * @author Yuri Mashkovtsev <yuri@mashkovtsev.ru>
 * @version 0.1
 */
class Request {

  private final String userName;
  private final String command;
  private final String arg;

  private static final Logger LOG = LoggerFactory.getLogger(Request.class);

  Request(String fromClient) throws SecurityException {

    if (fromClient == null || "".equals(fromClient)) throw new SecurityException("Empty request");

    final String[] fromClientSplitted = fromClient.split("\\s+", -1);

    if (fromClientSplitted.length != 3) throw new SecurityException("Wrong arguments number");

    this.userName = fromClientSplitted[0];
    this.command = fromClientSplitted[1];
    this.arg = fromClientSplitted[2];

    switch (this.command) {
      case "CREATE_TASK":
      case "CLOSE_TASK":
      case "DELETE_TASK":
      case "REOPEN_TASK":
      case "LIST_TASK":
        break;

      default:
        throw new SecurityException("Invalid command");
    }
  }

  /**
   * Routes object of Request class to appropriate direction in order to get processed. Has no
   * parameters because everything we might need is already in an object.
   *
   * @return result of request processing
   */
  String process() {

    String answer = "ERROR";

    User user = UsersPack.get(userName);
    // get() returned pseudo-user (no such user), create new one instead:
    if ("".equals(user.getName())) user = new User(userName);

    Task task = TasksPack.get(arg);

    final String taskName = task.getName();
    final boolean isTaskNew = "".equals(taskName);

    switch (command) {
      case "CREATE_TASK":
        LOG.info(
            "[?] User "
                + userName
                + " wishes to create "
                + (isTaskNew ? "new " : "")
                + "task '"
                + arg
                + "'");
        if (isTaskNew) {
          task = new Task(user, arg);
          answer = task.getStatus();
        } else {
          LOG.info("[-] Task '" + taskName + "' already exists, nobody is allowed to create it");
        }
        break;

      case "CLOSE_TASK":
        LOG.info(
            "[?] User "
                + userName
                + " wishes to close "
                + (isTaskNew ? "non-existent " : "")
                + "task '"
                + arg
                + "'"
                + (isTaskNew ? "" : " being in status " + task.getStatus()));
        if (isTaskNew) {
          LOG.info("[-] Task '" + arg + "' doesnt exist, couldn't be closed");
          break;
        }
        try {
          task.close(user);
        } catch (AccessDeniedException ea) {
          answer = ea.getMessage();
          break;
        } catch (TaskStatusViolationException es) {
          break;
        }
        answer = task.getStatus();
        break;

      case "DELETE_TASK":
        LOG.info(
            "[?] User "
                + userName
                + " wishes to delete "
                + (isTaskNew ? "non-existent " : "")
                + "task '"
                + arg
                + "'"
                + (isTaskNew ? "" : " being in status " + task.getStatus()));
        if (isTaskNew) {
          LOG.info("[-] Task '" + arg + "' doesnt exist, couldn't be deleted");
          break;
        }
        try {
          task.delete(user);
        } catch (AccessDeniedException ea) {
          answer = ea.getMessage();
          break;
        } catch (TaskStatusViolationException es) {
          break;
        }
        answer = task.getStatus();
        break;

      case "REOPEN_TASK":
        LOG.info(
            "[?] User "
                + userName
                + " wishes to reopen "
                + (isTaskNew ? "non-existent " : "")
                + "task '"
                + arg
                + "'"
                + (isTaskNew ? "" : " being in status " + task.getStatus()));
        if (isTaskNew) {
          LOG.info("[-] Task '" + arg + "' doesnt exist, couldn't be reopened");
          break;
        }
        try {
          task.reopen(user);
        } catch (AccessDeniedException ea) {
          answer = ea.getMessage();
          break;
        } catch (TaskStatusViolationException es) {
          break;
        }
        if ("CREATED".equals(task.getStatus())) answer = "REOPENED";
        break;

      case "LIST_TASK":
        user = UsersPack.get(arg);
        final boolean isUserUnknown = "".equals(user.getName());
        LOG.info(
            "[?] User "
                + userName
                + " wishes to get list of tasks owned by "
                + (isUserUnknown ? "unknown user " : "")
                + arg);
        if (isUserUnknown) {
          LOG.info("[-] No such user");
        } else answer = user.getTasks();
        break;

      default:
    }

    return answer;
  }
}
