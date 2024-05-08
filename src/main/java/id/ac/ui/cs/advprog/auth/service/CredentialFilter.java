package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class CredentialFilter extends BaseFilter{

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String authValue = request.getHeader("Authorization");
        if (!authValue.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            super.doFilter(request, response);
        }
    }
}
