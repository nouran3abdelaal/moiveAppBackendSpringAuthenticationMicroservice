package com.example.AuthenticationMicroservice.auth;

import com.example.AuthenticationMicroservice.user.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RegisterRequestTest {
    @Test
    @DisplayName("Testing the constructor and the getter methods")
    void testConstructorAndGetters() {
        RegisterRequest registerRequest = new RegisterRequest("John Doe", "john@example.com", "password123", Role.USER);

        assertEquals("John Doe", registerRequest.getName());
        assertEquals("john@example.com", registerRequest.getEmail());
        assertEquals("password123", registerRequest.getPassword());
        assertEquals(Role.USER, registerRequest.getRole());
    }

    @Test
        @DisplayName("Testing the builder")
    void testBuilder() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Alice Smith")
                .email("alice@example.com")
                .password("securePwd")
                .role(Role.ADMIN)
                .build();

        assertEquals("Alice Smith", registerRequest.getName());
        assertEquals("alice@example.com", registerRequest.getEmail());
        assertEquals("securePwd", registerRequest.getPassword());
        assertEquals(Role.ADMIN, registerRequest.getRole());
    }

    @Test
    @DisplayName("Testing the setters")
    void testSetter() {
        RegisterRequest registerRequest = new RegisterRequest("John Doe", "john@example.com", "password123", Role.USER);
        registerRequest.setName("Jane Smith");
        registerRequest.setEmail("jane@example.com");
        registerRequest.setPassword("newPassword");
        registerRequest.setRole(Role.ADMIN);

        assertEquals("Jane Smith", registerRequest.getName());
        assertEquals("jane@example.com", registerRequest.getEmail());
        assertEquals("newPassword", registerRequest.getPassword());
        assertEquals(Role.ADMIN, registerRequest.getRole());
    }
}