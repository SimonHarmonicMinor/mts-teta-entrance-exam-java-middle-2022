package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест должен:")
class UserRepositoryImplTest {

    UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("выполнять добавление юзера в репозиторий")
    void addUserTest() {
        assertDoesNotThrow(() -> userRepository.addUser(new User("DASHA", List.of())));
    }

    @Test
    @DisplayName("выполнять получение юзера")
    void getUserByNameTest() {
        User user = new User("DASHA", List.of());
        userRepository.addUser(user);
        assertEquals(Optional.of(user), userRepository.getUserByName("DASHA"));
    }

    @Test
    @DisplayName("выполнять получение списка юзеров")
    void getAllUsersTest() {
        User user = new User("DASHA", List.of());
        userRepository.addUser(user);
        assertEquals(List.of(user), userRepository.getAllUsers());
    }

}