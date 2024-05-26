package id.ac.ui.cs.advprog.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTGeneratorTest {

    private JWTGenerator jwtGenerator;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtGenerator = new JWTGenerator();
    }

    @Test
    void testGenerateToken() {
        when(authentication.getName()).thenReturn("testuser");

        String token = jwtGenerator.generateToken(authentication);

        assertNotNull(token);
    }

    @Test
    void testGetUsernameFromJWT() {
        when(authentication.getName()).thenReturn("testuser");

        String token = jwtGenerator.generateToken(authentication);
        String username = jwtGenerator.getUsernameFromJWT(token);

        assertNotNull(token);
        assertEquals(username, "testuser");
    }

    @Test
    void testValidateToken_Success() {
        when(authentication.getName()).thenReturn("testuser");

        String token = jwtGenerator.generateToken(authentication);

        assertTrue(jwtGenerator.validateToken(token));
    }

    @Test
    void testValidateToken_CannotCreateKey() {
        // Assuming SecurityConstants.JWT_EXPIRATION is defined somewhere in your project
        String username = "testuser";
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256), SignatureAlgorithm.HS256) // Different key to simulate failure
                .compact();

        assertFalse(jwtGenerator.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.token";

        assertFalse(jwtGenerator.validateToken(invalidToken));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        String username = "testuser";
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() - 1000); // Token already expired

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        assertFalse(jwtGenerator.validateToken(token));
    }

    @Test
    void testCreateSecurityConstants() {
        SecurityConstants constants = new SecurityConstants();
        assertNotNull(constants);
    }
}