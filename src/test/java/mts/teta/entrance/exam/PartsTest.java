package mts.teta.entrance.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PartsTest {

  private Request r;
  private User u;
  private Task t;

  @Test
  void test0100RequestClass() {

    try {
      r = new Request("");
    } catch (SecurityException sept) {
      assertEquals("Empty request", sept.getMessage());
    } finally {
      try {
        assertEquals("Something", r.process());
      } catch (NullPointerException npe) {
        assertEquals(null, npe.getMessage());
      }
    }
  }

  @Test
  void test0200UserClass() {

    u = new User("MARY");
    assertEquals("MARY", u.getName());
  }

  @Test
  void test0300TaskClass() {

    u = new User("MARY");
    t = new Task(u, "KickJohn");
    assertEquals("CREATED", t.getStatus());
    assertEquals("KickJohn", t.getName());

    try {
      t.reopen(u);
    } catch (AccessDeniedException ea) {
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("CREATED", t.getStatus());
      assertEquals("KickJohn", t.getName());
    }

    try {
      t.reopen(new User("John"));
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("CREATED", t.getStatus());
      assertEquals("KickJohn", t.getName());
    }

    try {
      t.reopen(u);
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("CREATED", t.getStatus());
      assertEquals("KickJohn", t.getName());
    }

    try {
      t.close(u);
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("CLOSED", t.getStatus());
      assertEquals("KickJohn", t.getName());
    }

    assertEquals("TASKS[KickJohn]", u.getTasks());

    try {
      t.delete(u);
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("DELETED", t.getStatus());
      assertEquals("KickJohn", t.getName());
    }
  }

  @Test
  void test0400UsersPackClass() {

    u = new User("JOHN");
    User uFromPack = UsersPack.get(u.getName());
    assertEquals("JOHN", uFromPack.getName());
  }

  @Test
  void test0500TasksPackClass() {

    u = new User("PAUL");
    t = new Task(u, "BiteMary");

    Task tFromPack = TasksPack.get(t.getName());
    assertEquals("BiteMary", tFromPack.getName());

    // cleanup, stage I
    try {
      tFromPack.close(u);
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("CLOSED", tFromPack.getStatus());
      assertEquals("TASKS[BiteMary]", u.getTasks());
    }
    // cleanup, stage II
    try {
      tFromPack.delete(u);
    } catch (AccessDeniedException ea) {
      assertEquals("ACCESS_DENIED", ea.getMessage());
    } catch (TaskStatusViolationException es) {
      assertEquals("ERROR", es.getMessage());
    } finally {
      assertEquals("DELETED", tFromPack.getStatus());
      assertEquals("TASKS[]", u.getTasks());
    }
  }
}
