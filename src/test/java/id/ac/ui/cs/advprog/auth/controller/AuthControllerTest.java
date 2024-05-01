package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn("fakeToken");

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("fakeToken", response.getBody().getAccessToken());
    }

    @Test
    public void testRegister() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("username");
        registerDto.setPassword("password");
        when(userRepository.findByUsername(registerDto.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<String> response = authController.register(registerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered success!", response.getBody());

        verify(userRepository, times(1)).createUser(any(UserEntity.class));
    }
}
