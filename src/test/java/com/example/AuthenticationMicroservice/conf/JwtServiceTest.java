package com.example.AuthenticationMicroservice.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {


    @InjectMocks
        private JwtService jwtService;

        @Mock
        private UserDetails userDetails;


        @Test
        @DisplayName("Testing extracting username method")
        void testExtractUsername() {
            String token = createSampleToken("testUser");
            String username = jwtService.extractUsername(token);
            assertEquals("testUser", username);
        }

        @Test
        @DisplayName("Testing extracting All claims and non null result returned")
        void testExtractAllClaims() {
            String token = createSampleToken("testUser");
            Claims claims = jwtService.extractAllClaims(token);
            assertNotNull(claims);
            assertEquals("testUser", claims.getSubject());
        }

        @Test
        @DisplayName("Testing generating a token with extra claims")
        void testGenerateTokenWithExtraClaims() {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("key1", "value1");
            extraClaims.put("key2", "value2");
            String token = jwtService.generateToken(extraClaims, userDetails);
            assertNotNull(token);
        }

        @Test
        @DisplayName("Testing generating a token with no extra claims")
        void testGenerateTokenWithoutExtraClaims() {
            String token = jwtService.generateToken(userDetails);
            assertNotNull(token);
        }

        @Test
        @DisplayName("Testing generating a valid token")
        void testIsTokenValidWithValidToken() {
            String token = createSampleToken("testUser");
            when(userDetails.getUsername()).thenReturn("testUser");
            boolean isValid = jwtService.isTokenValid(token, userDetails);
            assertTrue(isValid);
        }

        @Test
        @DisplayName("Testing generating a expired token")
        void testIsTokenValidWithExpiredToken() {
            String expiredToken = createSampleToken("testUser", -10000); // Expired token
            when(userDetails.getUsername()).thenReturn("testUser");
            assertThrows(ExpiredJwtException.class, () -> {
                jwtService.isTokenValid(expiredToken, userDetails);
            });
        }

        @Test
        @DisplayName("Testing the validity of a token with wrong userName")
        void testIsTokenValidWithInvalidUser() {
            String token = createSampleToken("testUser");
            when(userDetails.getUsername()).thenReturn("otherUser");
            boolean isValid = jwtService.isTokenValid(token, userDetails);
            assertFalse(isValid);
        }



        @Test
        @DisplayName("Testing extracting the expiration of a token ")
        void testExtractExpiration() {
            String token = createSampleToken("testUser");
            Date expiration = jwtService.extractExpiration(token);
            assertNotNull(expiration);
        }

        private String createSampleToken(String username) {
            return createSampleToken(username, 100000);
        }

        private String createSampleToken(String username, long expirationOffset) {
            //    @Value("${jwt.secretKey}")
            String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expirationOffset))
                    .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                    .compact();
        }
    }


