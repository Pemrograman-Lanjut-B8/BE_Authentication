package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ValidateTokenFilter extends BaseFilter{

    private JWTGenerator tokenGenerator;

    public void setTokenGenerator(JWTGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String authToken = request.getHeader("Authorization");
        String bearerToken = authToken.substring(7, authToken.length());
        if (!tokenGenerator.validateToken(bearerToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        } else {
            super.doFilter(request, response);
        }
    }
}
