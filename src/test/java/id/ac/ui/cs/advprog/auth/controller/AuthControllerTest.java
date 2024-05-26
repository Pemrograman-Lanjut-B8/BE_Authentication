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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("testpassword");

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken("testtoken");

        when(userService.login(any(LoginDto.class))).thenReturn(authResponseDto);

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testtoken", response.getBody().getToken());
    }

    @Test
    public void testLogin_Failure() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("testpassword");

        when(userService.login(any(LoginDto.class))).thenThrow(new RuntimeException("Login failed"));

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody().getToken());
    }

    @Test
    public void testRegisterUser_Success() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setPassword("testpassword");
        registerDto.setEmail("testuser@example.com");

        ResponseEntity<String> response = authController.registerUser(registerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody());
    }

    @Test
    public void testRegisterUser_Failure() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setPassword("testpassword");
        registerDto.setEmail("testuser@example.com");

        doThrow(new RuntimeException("Registration failed")).when(userService).create(any(RegisterDto.class));

        ResponseEntity<String> response = authController.registerUser(registerDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}