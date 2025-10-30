package com.comparus.api.controller;


import com.comparus.api.dto.UserDto;
import com.comparus.basic.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends TestContainersConfig {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void getAllUsers_shouldReturnCorrectUserData() {
        // When
        ResponseEntity<UserDto[]> response = rest.getForEntity("/users", UserDto[].class);
        var users = response.getBody();

        var userIds = java.util.Arrays.stream(users)
                .map(UserDto::id)
                .toList();

        // Then
        assertNotNull(users);
        assertEquals(3, users.length);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(userIds.contains("example-user-id-1"));
        assertTrue(userIds.contains("example-user-id-2"));
        assertTrue(userIds.contains("example-user-id-3"));
    }

    @Test
    void getAllUsers_shouldFilterByUsername() {
        // When
        UserDto[] users = rest.getForObject("/users?username=user-1", UserDto[].class);

        // Then
        assertNotNull(users);
        assertEquals(1, users.length, "Should return only one user");
        assertEquals("user-1", users[0].username());
        assertEquals("example-user-id-1", users[0].id());
    }

    @Test
    void getAllUsers_shouldFilterByMultipleParameters() {
        // When
        UserDto[] users = rest.getForObject("/users?name=User&surname=Userenko", UserDto[].class);

        // Then
        assertNotNull(users);
        assertEquals(1, users.length, "Should return only one user");
        assertEquals("User", users[0].name());
        assertEquals("Userenko", users[0].surname());
        assertEquals("user-1", users[0].username());
    }

    @Test
    void getAllUsers_shouldReturnEmptyWhenNoMatch() {
        // When
        UserDto[] users = rest.getForObject("/users?username=nonexistent", UserDto[].class);

        // Then
        assertNotNull(users);
        assertEquals(0, users.length, "Should return empty array when no users match");
    }

    @Test
    void getAllUsers_shouldIgnoreEmptyFilters() {
        // When
        UserDto[] allUsers = rest.getForObject("/users", UserDto[].class);
        UserDto[] filteredUsers = rest.getForObject("/users?username=", UserDto[].class);

        // Then
        assertNotNull(allUsers);
        assertNotNull(filteredUsers);
        assertEquals(allUsers.length, filteredUsers.length,
                "Empty filter should return same results as no filter");
    }

    @Test
    void getAllUsers_shouldFilterByAllThreeParameters() {
        // When
        UserDto[] users = rest.getForObject(
                "/users?username=user-3&name=Maria&surname=Ivanova",
                UserDto[].class);

        // Then
        assertNotNull(users);
        assertEquals(1, users.length, "Should return user matching all criteria");
        assertEquals("user-3", users[0].username());
        assertEquals("Maria", users[0].name());
        assertEquals("Ivanova", users[0].surname());
    }

}