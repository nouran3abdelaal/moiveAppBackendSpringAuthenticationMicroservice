package com.example.MoiveProject.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthenticationRequestTest {
    @Test
    @DisplayName("Testing the constructor and the getter methods")
    void testConstructorAndGetters() {
        AuthenticationRequest authRequest = new AuthenticationRequest("john@example.com", "password123");

        assertAll(
                ()->assertEquals("john@example.com", authRequest.getEmail()),
                ()->assertEquals("password123", authRequest.getPassword())
        );


    }

    @Test
    @DisplayName("Testing the builder")
    void testBuilder() {
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email("alice@example.com")
                .password("securePwd")
                .build();
        assertAll(
                ()-> assertEquals("alice@example.com", authRequest.getEmail()),
                ()->        assertEquals("securePwd", authRequest.getPassword())
        );


    }

    @Test
    @DisplayName("Testing the setters")
    void testSetter() {
        AuthenticationRequest authRequest = new AuthenticationRequest("john@example.com", "password123");
        authRequest.setEmail("jane@example.com");
        authRequest.setPassword("newPassword");
        assertAll(
                ()->         assertEquals("jane@example.com", authRequest.getEmail()),
                ()->        assertEquals("newPassword", authRequest.getPassword())
        );

    }
}