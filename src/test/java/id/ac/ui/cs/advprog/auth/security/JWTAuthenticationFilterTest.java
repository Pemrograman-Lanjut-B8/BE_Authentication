package id.ac.ui.cs.advprog.auth.security;

import id.ac.ui.cs.advprog.auth.service.CredentialFilter;
import id.ac.ui.cs.advprog.auth.service.HeaderFilter;
import id.ac.ui.cs.advprog.auth.service.ValidateTokenFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JWTAuthenticationFilterTest {

    @InjectMocks
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JWTGenerator tokenGenerator;

    @Mock
    private UserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternalWithUnauthorizedResponse() throws ServletException, IOException {
        when(response.getStatus()).thenReturn(HttpServletResponse.SC_UNAUTHORIZED);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(tokenGenerator, never()).getUsernameFromJWT(anyString());
        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        String token = "Bearer validToken";
        String username = "testuser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenGenerator.validateToken("validToken")).thenReturn(true);
        when(tokenGenerator.getUsernameFromJWT("validToken")).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenGenerator).validateToken("validToken");
        verify(tokenGenerator).getUsernameFromJWT("validToken");
        verify(customUserDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
        String token = "Bearer invalidToken";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenGenerator.validateToken("invalidToken")).thenReturn(false);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(tokenGenerator).validateToken("invalidToken");
        verify(tokenGenerator, never()).getUsernameFromJWT(anyString());
        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternalWithExpiredTokenException() throws ServletException, IOException {
        String token = "Bearer expiredToken";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenGenerator.validateToken("expiredToken")).thenReturn(false);
        doThrow(new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect"))
                .when(tokenGenerator).getUsernameFromJWT("expiredToken");

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(tokenGenerator).validateToken("expiredToken");
        verify(tokenGenerator, never()).getUsernameFromJWT("expiredToken");
        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
    }
}