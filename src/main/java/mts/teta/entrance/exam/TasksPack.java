package mts.teta.entrance.exam;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains shared data structure called "pack" to put all the tasks together.
 *
 * @author Yuri Mashkovtsev <yuri@mashkovtsev.ru>
 * @version 0.1
 */
class TasksPack {

  private static volatile Set<Task> pack = new HashSet<>();

  /**
   * Provides desired single task from TasksPack or dummy task if not found.
   *
   * @param taskName is name of task
   * @return task object
   */
  static synchronized Task get(String taskName) {

    for (Task task : pack)
      if (!"DELETED".equals(task.getStatus()) && taskName.equals(task.getName())) return task;

    return new Task(); // no such task
  }

  /**
   * Adds given task to the pack of tasks.
   *
   * @param task is object of Task class
   */
  static synchronized void add(Task task) {

    pack.add(task);
  }
}
