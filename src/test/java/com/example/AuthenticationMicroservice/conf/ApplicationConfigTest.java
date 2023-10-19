package com.example.AuthenticationMicroservice.conf;

import com.example.AuthenticationMicroservice.repositories.UserRepository;
import com.example.AuthenticationMicroservice.user.Role;
import com.example.AuthenticationMicroservice.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Import({ApplicationConfig.class, AuthenticationConfiguration.class})

class ApplicationConfigTest {
    @InjectMocks
    private ApplicationConfig applicationConfig;
    private final AuthenticationConfiguration authenticationConfiguration;
@Autowired
    public ApplicationConfigTest(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Test getting an existing user")
    public void testUserDetailsService() {
        String username = "user@example.com";

        UserEntity userDetails = new UserEntity(username,"nouran","pass1", Role.USER);


        when(userRepository.findByEmail(username)).thenReturn(Optional.of(userDetails));

        UserDetails result = applicationConfig.userDetailsService().loadUserByUsername(username);

        assertEquals(username, result.getUsername());
    }
    @Test
    @DisplayName("Test searching for non existing user")
    public void testUserDetailsServiceUserNotFound() {
        String username = "nonexistent@example.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        try {
            applicationConfig.userDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
    }
    @Test
    @DisplayName("Testing Authentication Provider method")
    public void testAuthenticationProvider() {
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();
        assertNotNull(authenticationProvider);
    }
    @Test
    @DisplayName("Testing Password Encoder method")
    public void testPasswordEncoder() {
        PasswordEncoder result = applicationConfig.passwordEncoder();
        assertNotNull(result);
        assertTrue(result instanceof BCryptPasswordEncoder);
    }
    @Test
    @DisplayName("Testing Authentication manger method")
    public void testAuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        assertNotNull(authenticationManager);
    }

}