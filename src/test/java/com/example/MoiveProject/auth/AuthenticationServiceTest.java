package com.example.MoiveProject.auth;

import com.example.MoiveProject.conf.JwtService;
import com.example.MoiveProject.repositories.UserRepository;
import com.example.MoiveProject.user.Role;
import com.example.MoiveProject.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Testing register a user")
    void register() {
        RegisterRequest request = new RegisterRequest("username", "password", "user@example.com", Role.USER);

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        Mockito.when(jwtService.generateToken(userEntity)).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        assertEquals("jwtToken", response.getToken());

    }

    @Test
    @DisplayName("Test logging in")
    public void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("user@example.com", "password");

        UserEntity userEntity = UserEntity.builder()
                .name("username")
                .email(request.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userEntity));
        Mockito.when(jwtService.generateToken(userEntity)).thenReturn("jwtToken");
        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("jwtToken", response.getToken());
    }
}