package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.RoleRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLogin_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(1L, "username", "email", "password", Collections.emptyList()));
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mock JWT Token generation
        when(jwtGenerator.generateToken(authentication)).thenReturn("mocked_token");

        // Test Login
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");
        ResponseEntity<AuthResponseDto> responseEntity = authController.login(loginDto);

        // Verify authentication manager was called with correct parameters
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Verify token generation
        assertEquals("mocked_token", responseEntity.getBody().getToken());

        // Verify user roles (you can use getAuthorities() in your logic)
        assertEquals(Collections.emptyList(), ((UserDetailsImpl) authentication.getPrincipal()).getAuthorities());
    }


    @Test
    public void testRegister_Success() throws Throwable {
        // Mock UserRepository
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Mock Password Encoder
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

        // Mock RoleRepository
        when(roleRepository.findByName(any())).thenReturn(java.util.Optional.of(new Role(ERole.ROLE_USER)));

        // Test Register
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("username");
        registerDto.setEmail("email");
        registerDto.setPassword("password");
        registerDto.setRole(Collections.singleton("ROLE_USER"));
        ResponseEntity<?> responseEntity = authController.registerUser(registerDto);

        // Verify user creation
        verify(userRepository).save(any(UserEntity.class));

        // Verify successful registration response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered success!", responseEntity.getBody());
    }
}
