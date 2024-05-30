package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FilterChainTest {

    private HeaderFilter headerFilter;
    private CredentialFilter credentialFilter;
    private ValidateTokenFilter validateTokenFilter;
    private JWTGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        headerFilter = new HeaderFilter();
        credentialFilter = new CredentialFilter();
        validateTokenFilter = new ValidateTokenFilter();
        tokenGenerator = mock(JWTGenerator.class);

        headerFilter.setNextFilter(credentialFilter);
        credentialFilter.setNextFilter(validateTokenFilter);
        validateTokenFilter.setTokenGenerator(tokenGenerator);
    }

    @Test
    void testHeaderFilterWithoutAuthorizationHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        headerFilter.doFilter(request, response);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void testCredentialFilterWithoutBearer() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Basic abcdef");

        headerFilter.doFilter(request, response);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void testValidateTokenFilterWithInvalidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        when(tokenGenerator.validateToken("invalidtoken")).thenReturn(false);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            headerFilter.doFilter(request, response);
        });

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(tokenGenerator).validateToken("invalidtoken");
    }

    @Test
    void testFilterChainWithValidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(tokenGenerator.validateToken("validtoken")).thenReturn(true);

        headerFilter.doFilter(request, response);

        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(tokenGenerator).validateToken("validtoken");
    }
}