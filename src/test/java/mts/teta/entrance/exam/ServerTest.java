package mts.teta.entrance.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  /*
   * Tests 0000 - 0100. First, lets check how server handles validity of incoming mesages.
   *
   */

  // Test wrong number of lexems (1)
  @Test
  void test0010() {
    String response = sendMessage("How much is THE FISH ?");
    assertEquals("WRONG_FORMAT", response);
  }

  // Test wrong number of lexems (2)
  @Test
  void test0020() {
    String response = sendMessage("TAKE CARE!");
    assertEquals("WRONG_FORMAT", response);
  }

  // Test wrong number of lexems (3) - no lexems al all
  @Test
  void test0030() {
    String response = sendMessage("");
    assertEquals("WRONG_FORMAT", response);
  }

  // Test unrecognizable message (1)
  @Test
  void test0040() {
    String response = sendMessage("100 +200 -300");
    assertEquals("WRONG_FORMAT", response);
  }

  // Test unrecognizable message (2)
  @Test
  void test0050() {
    String response = sendMessage("PETER GET_BACK HOME");
    assertEquals("WRONG_FORMAT", response);
  }

  // Test unrecognizable message (3)
  @Test
  void test0060() {
    String response =
        sendMessage(
            "43082d77d8911cd705272f46465b6fb8 d1423e78369c951778d41927bf3113e1 728e49e2f9a7136e87a7d215b8bb3f86");
    assertEquals("WRONG_FORMAT", response);
  }

  /*
   * Tests 0101 - 0200. Let's inspect "CREATE_TASK" feature
   *
   */

  @Test
  void test0110() {
    String response = sendMessage("MARY CREATE_TASK");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test0120() {
    String response = sendMessage("MARY CREATE_TASK BiteJohn");
    assertEquals("CREATED", response);
  }

  @Test
  void test0130() {
    String response = sendMessage("MARY CREATE_TASK BiteJohn"); // dont repeat yourself
    assertEquals("ERROR", response);
  }

  @Test
  void test0140() {
    String response = sendMessage("PETER CREATE_TASK BiteJohn"); // dont repeat your friend
    assertEquals("ERROR", response);
  }

  @Test
  void test0150() {
    String response = sendMessage("MARY CREATE_TASK CallPeter");
    assertEquals("CREATED", response);
  }

  // Well Ok, but can we create task with the same name as just deleted task? Lets check.
  @Test
  void test0160() {
    String response = sendMessage("MARY CREATE_TASK BlamePeter");
    assertEquals("CREATED", response);
  }

  @Test
  void test0170() {
    String response = sendMessage("MARY CLOSE_TASK BlamePeter");
    assertEquals("CLOSED", response);
  }

  @Test
  void test0180() {
    String response = sendMessage("MARY DELETE_TASK BlamePeter");
    assertEquals("DELETED", response);
  }

  @Test
  void test0190() {
    String response = sendMessage("MARY CREATE_TASK BlamePeter");
    assertEquals(
        "CREATED", response); // now we've got TWO tasks named 'BlamePeter' (hope this is all right)
  }

  // Can task name be the same as user's name ?
  @Test
  void test0192() {
    String response = sendMessage("JOHN CREATE_TASK JOHN");
    assertEquals("CREATED", response); // yes it can
  }

  @Test
  void test0194() {
    String response = sendMessage("JOHN CLOSE_TASK JOHN");
    assertEquals("CLOSED", response);
  }

  @Test
  void test0196() {
    String response = sendMessage("JOHN DELETE_TASK JOHN");
    assertEquals("DELETED", response); // I dont love you anymore
  }

  /*
   * Tests 0201 - 0300. Let's inspect "DELETE_TASK" feature
   *
   */

  @Test
  void test0210() {
    String response = sendMessage("DELETE_TASK EnslaveTheUniverse");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test0220() {
    String response = sendMessage("PETER DELETE_TASK EnslaveTheUniverse");
    assertEquals("ERROR", response); // obviously there is no such task
  }

  // Try to delete CREATED task
  @Test
  void test0230() {
    String response = sendMessage("MARY DELETE_TASK BiteJohn");
    assertEquals("ERROR", response); // only CLOSED task can be deleted
  }

  @Test
  void test0240() {
    String response = sendMessage("MARY CLOSE_TASK BiteJohn");
    assertEquals("CLOSED", response);
  }

  @Test
  void test0250() {
    String response = sendMessage("MARY DELETE_TASK BiteJohn");
    assertEquals("DELETED", response);
  }

  // is Mary able to delete it again ?
  @Test
  void test0255() {
    String response = sendMessage("MARY DELETE_TASK BiteJohn");
    assertEquals("ERROR", response); // no, she isn't
  }

  // Lets try Mary to delete someone else's task
  @Test
  void test0260() {
    String response = sendMessage("JOHN CREATE_TASK GreetMary");
    assertEquals("CREATED", response);
  }

  // Task 'GreetMary' cannot be deleted for two reasons: 1) it owned by John; 2) it is NOT in status
  // CLOSED. Lets make sure check priorities are ok. Of course, ACCESS_DENIED has highest priority.
  @Test
  void test0270() {
    String response = sendMessage("MARY DELETE_TASK GreetMary");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test0280() {
    String response = sendMessage("JOHN DELETE_TASK GreetMary");
    assertEquals("ERROR", response);
  }

  @Test
  void test0290() {
    String response = sendMessage("JOHN CLOSE_TASK GreetMary");
    assertEquals("CLOSED", response);
  }

  // Even now Mary should have no chances to delete task 'GreetMary'
  @Test
  void test0295() {
    String response = sendMessage("MARY DELETE_TASK GreetMary");
    assertEquals("ACCESS_DENIED", response);
  }

  /*
   * Tests 0301 - 0400. Let's inspect "CLOSE_TASK" feature
   *
   */

  @Test
  void test0310() {
    String response = sendMessage("CLOSE_TASK EnslaveTheWorld");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test0320() {
    String response = sendMessage("PETER CLOSE_TASK EnslaveTheWorld");
    assertEquals("ERROR", response); // obviously there is no such task
  }

  // Try to close DELETED task
  @Test
  void test0330() {
    String response = sendMessage("MARY CLOSE_TASK BiteJohn");
    assertEquals("ERROR", response);
  }

  @Test
  void test0340() {
    String response = sendMessage("MARY CREATE_TASK SaveMoney");
    assertEquals("CREATED", response);
  }

  @Test
  void test0350() {
    String response = sendMessage("MARY CLOSE_TASK SaveMoney");
    assertEquals("CLOSED", response);
  }

  // Try to close CLOSED task
  @Test
  void test0360() {
    String response = sendMessage("MARY CLOSE_TASK SaveMoney");
    assertEquals("ERROR", response);
  }

  // Lets try Mary to close someone else's task
  @Test
  void test0370() {
    String response = sendMessage("MARY CLOSE_TASK GreetMary");
    assertEquals("ACCESS_DENIED", response);
  }

  /*
   * Tests 0401 - 0500. Let's inspect "REOPEN_TASK" feature
   *
   */

  @Test
  void test0410() {
    String response = sendMessage("REOPEN_TASK EnslaveTheWorld");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test0420() {
    String response = sendMessage("PETER REOPEN_TASK EnslaveTheWorld");
    assertEquals("ERROR", response); // obviously there is no such task
  }

  // Try to reopen DELETED task
  @Test
  void test0430() {
    String response = sendMessage("MARY REOPEN_TASK BiteJohn");
    assertEquals("ERROR", response);
  }

  // Try to reopen CLOSED task
  @Test
  void test0440() {
    String response = sendMessage("MARY REOPEN_TASK SaveMoney");
    assertEquals("REOPENED", response);
  }

  // Try to reopen the same task again
  @Test
  void test0450() {
    String response = sendMessage("MARY REOPEN_TASK SaveMoney");
    assertEquals("ERROR", response);
  }

  // Lets try Mary to reopen someone else's task.
  @Test
  void test0460() {
    String response = sendMessage("MARY REOPEN_TASK GreetMary");
    assertEquals("ACCESS_DENIED", response);
  }

  /*
   * Tests 0501 - 0600. Lastly, let's inspect "LIST_TASK" feature
   *
   */

  @Test
  void test0510() {
    String response = sendMessage("MARY LIST_TASK PETER");
    assertEquals("TASKS[]", response); // must be empty set
  }

  @Test
  void test0520() {
    String response = sendMessage("PETER LIST_TASK JOHN");
    assertEquals("TASKS[GreetMary]", response); // must have single non-deleted task
  }

  @Test
  void test0530() {
    String response = sendMessage("JOHN LIST_TASK MARY");
    assertEquals(
        "TASKS[BlamePeter, CallPeter, SaveMoney]", response); // Mary must have 3 non-deleted tasks
  }

  @Test
  void test0540() {
    String response = sendMessage("MARY CREATE_TASK KillEverybody");
    assertEquals("CREATED", response);
  }

  @Test
  void test0550() {
    String response = sendMessage("MARY LIST_TASK MARY");
    assertEquals(
        "TASKS[BlamePeter, CallPeter, KillEverybody, SaveMoney]",
        response); // already four, as we can see
  }

  @Test
  void test0560() {
    String response = sendMessage("MARY CLOSE_TASK BlamePeter");
    assertEquals("CLOSED", response);
  }

  @Test
  void test0570() {
    String response = sendMessage("MARY LIST_TASK MARY");
    assertEquals(
        "TASKS[BlamePeter, CallPeter, KillEverybody, SaveMoney]",
        response); // must be still four non-deleted tasks
  }

  @Test
  void test0580() {
    String response = sendMessage("MARY DELETE_TASK BlamePeter");
    assertEquals("DELETED", response);
  }

  @Test
  void test0590() {
    String response = sendMessage("MARY LIST_TASK MARY");
    assertEquals(
        "TASKS[CallPeter, KillEverybody, SaveMoney]", response); // only 3 remains this time
  }

  // Lets try to get some stranger's task list
  @Test
  void test0595() {
    String response = sendMessage("MARY LIST_TASK GEORGE");
    assertEquals("ERROR", response); // no such user
  }

  /*
   * Well let me ask you once again: anyway, how much is the fish?
   *
   */

  @Test
  void test0610() {
    String response = sendMessage("How much is THAT F(#%^$@)INE FISH? HOW MUCH IS IT ANYWAY ?!");
    // are you asking me how much the fish is? well I can tell you how much is it. listen to me:
    // THAT PRETTY FISH IS HUGE.
    assertEquals("WRONG_FORMAT", response);
  }
}
