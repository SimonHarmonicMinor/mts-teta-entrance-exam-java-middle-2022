package mts.teta.entrance.exam;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains shared data structure called "pack" to put all the users together.
 *
 * @author Yuri Mashkovtsev <yuri@mashkovtsev.ru>
 * @version 0.1
 */
class UsersPack {

  private static volatile Set<User> pack = new HashSet<>();

  /**
   * Provides desired single user from UsersPack or dummy user if not found.
   *
   * @param userName is name of user
   * @return user object
   */
  static synchronized User get(String userName) {

    for (User user : pack) if (userName.equals(user.getName())) return user;

    return new User(); // no such user
  }

  /**
   * Adds given user to the pack of users.
   *
   * @param user is object of User class
   */
  static synchronized void add(User user) {

    pack.add(user);
  }
}
