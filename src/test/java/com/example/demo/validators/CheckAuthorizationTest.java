package com.example.demo.validators;

import com.example.demo.AbstractDatabaseSetUp;
import com.example.demo.database.IDatabase;
import com.example.demo.database.InMemoryDatabase;
import com.example.demo.models.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CheckAuthorizationTest extends AbstractDatabaseSetUp {

    private final CheckAuthorization checkAuthorization = new CheckAuthorization();

    @Test
    void checkAuthorize() {
        IDatabase cleanDB = new InMemoryDatabase();
        Request requestForCleanDb = new Request("user", "LIST_TASK", "user1");
        Request requestNotExistUser = new Request("user", "CLOSE_TASK", "task1");
        Request requestExistUserWithPermission = new Request("user1", "CLOSE_TASK", "task1");

        assertFalse(checkAuthorization.checkAuthorize(requestForCleanDb, cleanDB));
        assertFalse(checkAuthorization.checkAuthorize(requestNotExistUser, spyDb));
        assertTrue(checkAuthorization.checkAuthorize(requestExistUserWithPermission, spyDb));

        Mockito.verify(spyDb, Mockito.atLeast(2)).getUsers();
    }
}