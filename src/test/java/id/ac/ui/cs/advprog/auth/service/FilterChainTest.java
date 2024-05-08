package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.service.CredentialFilter;
import id.ac.ui.cs.advprog.auth.service.HeaderFilter;
import id.ac.ui.cs.advprog.auth.service.ValidateTokenFilter;
import static org.junit.jupiter.api.Assertions.assertThrows;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilterChainTest {

    @Mock
    private JWTGenerator tokenGenerator;

    @InjectMocks
    private HeaderFilter firstHandler;

    @InjectMocks
    private CredentialFilter secondHandler;

    @InjectMocks
    private ValidateTokenFilter thirdHandler;

    @Test
    public void testFilterChain_Unauthorized() throws ServletException, IOException {
        firstHandler.setNextFilter(secondHandler);
        secondHandler.setNextFilter(thirdHandler);
        thirdHandler.setTokenGenerator(tokenGenerator);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        firstHandler.doFilter(request, response);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testFilterChain_AuthenticationError() throws ServletException, IOException {
        firstHandler.setNextFilter(secondHandler);
        secondHandler.setNextFilter(thirdHandler);
        thirdHandler.setTokenGenerator(tokenGenerator);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenGenerator.validateToken("invalid-token")).thenReturn(false);

        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            firstHandler.doFilter(request, response);
        });
    }
}
