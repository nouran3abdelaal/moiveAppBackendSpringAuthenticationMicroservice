package com.example.MoiveProject.conf;

import com.example.MoiveProject.user.Role;
import com.example.MoiveProject.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;
    @InjectMocks
   private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("Testing with valid token")
    void testDoFilterInternal_WithValidToken() throws Exception {
        String validToken = "validToken";
        String userEmail = "user@example.com";

        when(jwtService.extractUsername(validToken)).thenReturn(userEmail);
        when(jwtService.isTokenValid(eq(validToken),any(UserDetails.class))).thenReturn(true);

        UserDetails userDetails =  UserEntity.builder()
                .email(userEmail)
                        .role(Role.USER)
                                .password("password")
                                        .build();

        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + validToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        jwtAuthenticationFilter.doFilter(request, response, filterChain);
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    @Test
    @DisplayName("Testing with invalid token")
    void testDoFilterInternal_WithInvalidToken() throws Exception {
        String invalidToken = "invalidToken";

        when(jwtService.extractUsername(invalidToken)).thenReturn(null);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + invalidToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    @Test
    @DisplayName("Testing with no token")
    void testDoFilterInternal_WithNoToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        jwtAuthenticationFilter.doFilter(request, response, filterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    }