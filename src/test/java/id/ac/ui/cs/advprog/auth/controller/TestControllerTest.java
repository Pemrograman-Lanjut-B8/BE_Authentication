package id.ac.ui.cs.advprog.auth.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestControllerTest {

    @InjectMocks
    private TestController testController;

    @Test
    public void testAllAccess() {
        String result = testController.allAccess();
        assertEquals("Public Content.", result);
    }

    @Test
    public void testUserAccess() {
        // Mock Authentication with user role
        Authentication auth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", null));
        String result = testController.userAccess();
        assertEquals("User Content.", result);
    }

    @Test
    public void testAdminAccess() {
        // Mock Authentication with admin role
        Authentication auth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", null));
        String result = testController.adminAccess();
        assertEquals("Admin Content.", result);
    }
}
