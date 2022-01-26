package com.example.demo;

import com.example.demo.enums.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

    @Test
    void test() {
        String response = sendMessage("request");
        assertEquals(Result.WRONG_FORMAT.name(), response);
    }

    @Test
    void test2() {
        String response = sendMessage("request2");
        assertEquals(Result.WRONG_FORMAT.name(), response);
    }

    @Test
    void create_tasks_must_return_created() {
        String response = sendMessage("PETYA CREATE_TASK CleanRoom");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("PETYA CREATE_TASK Clean_another_Room");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("NOT_PETYA CREATE_TASK CleanCar");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("NOT_PETYA CREATE_TASK Wash_Car");
        assertEquals(Result.CREATED.name(), response);
    }

    @Test
    void create_already_existed_tasks_must_return_error() {
        String response = sendMessage("NADIA CREATE_TASK Cook_borsh");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("NADIA CREATE_TASK Cook_borsh");
        assertEquals(Result.ERROR.name(), response);

        response = sendMessage("NOT_NADIA CREATE_TASK Cook_borsh");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    void create_and_immediately_delete_task_must_be_error() {
        String response = sendMessage("SVETA CREATE_TASK To_babysit");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("SVETA DELETE_TASK To_babysit");
        assertEquals(Result.ERROR.name(), response);

        response = sendMessage("SVETA CREATE_TASK To_babysit");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    void create_close_delete_and_create_again_tasks_must_be_ok() {
        String response = sendMessage("VANIA CREATE_TASK To_drink");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("VANIA CLOSE_TASK To_drink");
        assertEquals(Result.CLOSED.name(), response);

        response = sendMessage("VANIA DELETE_TASK To_drink");
        assertEquals(Result.DELETED.name(), response);

        response = sendMessage("VANIA CREATE_TASK To_drink");
        assertEquals(Result.CREATED.name(), response);
    }

    @Test
    void list_tasks_must_be_ok() {
        String response = sendMessage("IVAN CREATE_TASK To_smile");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("IVAN CREATE_TASK To_laugh");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("IVAN LIST_TASK IVAN");
        assertEquals("TASKS [To_smile, To_laugh]", response);

        response = sendMessage("PETIA LIST_TASK IVAN");
        assertEquals("TASKS [To_smile, To_laugh]", response);

        response = sendMessage("PETIA LIST_TASK ROMA");
        assertEquals("TASKS []", response);

        response = sendMessage("PETIA LIST_TASK");
        assertEquals("TASKS []", response);
    }

    @Test
    void change_other_user_task_status_must_be_error() {
        String response = sendMessage("PETIA CREATE_TASK ToSwim");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("NOT_PETIA CLOSE_TASK ToSwim");
        assertEquals(Result.ACCESS_DENIED.name(), response);
    }

    @Test
    void reopen_deleted_task_status_must_be_error() {
        String response = sendMessage("PETIA CREATE_TASK ToHaste");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("PETIA CLOSE_TASK ToHaste");
        assertEquals(Result.CLOSED.name(), response);

        response = sendMessage("PETIA DELETE_TASK ToHaste");
        assertEquals(Result.DELETED.name(), response);

        response = sendMessage("PETIA REOPEN_TASK ToHaste");
        assertEquals(Result.ERROR.name(), response);
    }

    @Test
    void reopen_task_status_must_be_ok() {
        String response = sendMessage("PETIA CREATE_TASK To_pray");
        assertEquals(Result.CREATED.name(), response);

        response = sendMessage("PETIA CLOSE_TASK To_pray");
        assertEquals(Result.CLOSED.name(), response);

        response = sendMessage("PETIA REOPEN_TASK To_pray");
        assertEquals(Result.REOPENED.name(), response);
    }

    @Test
    void wrong_request_format_must_be_error() {
        String response = sendMessage("PETIA TASK_MNE_ZAPILI To_pray");
        assertEquals(Result.WRONG_FORMAT.name(), response);

        response = sendMessage("PETIA 1 To_pray");
        assertEquals(Result.WRONG_FORMAT.name(), response);

        response = sendMessage("PETIA  To_pray");
        assertEquals(Result.WRONG_FORMAT.name(), response);

        response = sendMessage(null);
        assertEquals(Result.WRONG_FORMAT.name(), response);
    }
}