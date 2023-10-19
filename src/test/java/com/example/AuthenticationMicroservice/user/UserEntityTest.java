package com.example.AuthenticationMicroservice.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class UserEntityTest {
    @Test
    @DisplayName("Testing UserEntity implements the userDetails interface")
    public void testUserEntityImplementsUserDetails() {
        UserEntity user = new UserEntity();
        assertTrue(user instanceof UserDetails);
    }
    @Test
    @DisplayName("Testing Validating the UserDetails methods")
    public void testValidatingUserDetailsMethods() {
        UserEntity userDetails = UserEntity.builder()
                .email("test@example.com")
                .name("Test User")
                .password("password")
                .role(Role.USER)
                .build();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertAll(
                ()->        assertEquals("test@example.com", userDetails.getUsername()),
                ()->        assertEquals("password", userDetails.getPassword()),
                ()->        assertTrue(userDetails.isAccountNonExpired()),
                ()->        assertTrue(userDetails.isAccountNonLocked()),
                ()->        assertTrue(userDetails.isCredentialsNonExpired()),
                ()->        assertTrue(userDetails.isEnabled()),
                ()->        assertEquals(1, authorities.size()),
                ()->        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER")))
        );

    }

}