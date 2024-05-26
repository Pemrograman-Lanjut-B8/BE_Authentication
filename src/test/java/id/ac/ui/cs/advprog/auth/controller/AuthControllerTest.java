package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("testpassword");

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken("testtoken");

        when(userService.login(any(LoginDto.class))).thenReturn(authResponseDto);

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testtoken", Objects.requireNonNull(response.getBody()).getToken());
    }

    @Test
    void testLogin_Failure() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("testpassword");

        when(userService.login(any(LoginDto.class))).thenThrow(new RuntimeException("Login failed"));

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getToken());
    }

    @Test
    void testRegisterUser_Success() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setPassword("testpassword");
        registerDto.setEmail("testuser@example.com");

        ResponseEntity<String> response = authController.registerUser(registerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody());
    }

    @Test
    void testRegisterUser_Failure() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setPassword("testpassword");
        registerDto.setEmail("testuser@example.com");

        doThrow(new RuntimeException("Registration failed")).when(userService).create(any(RegisterDto.class));

        ResponseEntity<String> response = authController.registerUser(registerDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}