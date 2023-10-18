package com.example.MoiveProject.auth;

import com.example.MoiveProject.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

        @InjectMocks
        private AuthenticationController authenticationController;

        @Mock
        private AuthenticationService service;



        @Test
        @DisplayName("testRegister with empty request")
        public void testRegister() {
            RegisterRequest request = new RegisterRequest();
            AuthenticationResponse response = new AuthenticationResponse("token");
            Mockito.when(service.register(request)).thenReturn(response);

            ResponseEntity<AuthenticationResponse> result = authenticationController.register(request);

            assertAll(
                    ()-> assertEquals(HttpStatus.OK, result.getStatusCode()),
                    ()->assertEquals(response, result.getBody())
            );


        }
    @Test
    @DisplayName("testRegister with none empty request")
    public void testRegisterWithBody() {
        RegisterRequest request = new RegisterRequest("nouran","nona@gmail.com","pass", Role.USER);
        AuthenticationResponse response = new AuthenticationResponse("token");
        Mockito.when(service.register(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.register(request);

        assertAll(
                ()-> assertEquals(HttpStatus.OK, result.getStatusCode()),
                ()->assertEquals(response, result.getBody())
        );


    }
    @Test
    @DisplayName("testAuthenticate with empty request")
    public void testAuthenticate() {

        AuthenticationRequest request = new AuthenticationRequest();

        AuthenticationResponse response = new AuthenticationResponse("token");
        Mockito.when(service.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.authenticate(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
    @Test
    @DisplayName("testAuthenticate with none empty request")
    public void testAuthenticateWithBody() {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");

        AuthenticationResponse response = new AuthenticationResponse("token");
        Mockito.when(service.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.authenticate(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
    }


