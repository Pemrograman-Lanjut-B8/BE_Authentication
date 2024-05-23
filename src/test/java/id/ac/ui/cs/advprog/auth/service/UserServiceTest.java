package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.dto.UserDto;
import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.RoleRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterDto registerDto;
    private UserEntity userEntity;
    private Role userRole;

    @BeforeEach
    public void setUp() {
        // Initialize your test data here
        registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password123");
        registerDto.setRole(Set.of("user"));

        userEntity = new UserEntity();
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRole = new Role();
        userRole.setName(ERole.ROLE_USER);

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
    }

    @Test
    public void whenCreateUser_thenReturnUserEntity() {
        // Mock the existence check to return false
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);

        UserEntity createdUser = userService.create(registerDto);

        assertNotNull(createdUser);
        assertEquals(registerDto.getUsername(), createdUser.getUsername());
        assertEquals(registerDto.getEmail(), createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        assertTrue(createdUser.getRoles().contains(userRole));
    }

    @Test
    public void whenCreateUserWithExistingUsername_thenThrowException() {
        // Mock the existence check to return true
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.create(registerDto);
        });

        assertEquals("Username already exists: " + registerDto.getUsername(), exception.getMessage());
    }

    @Test
    public void whenCreateUserWithExistingEmail_thenThrowException() {
        // Mock the existence check to return true
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.create(registerDto);
        });

        assertEquals("Email already exists: " + registerDto.getEmail(), exception.getMessage());
    }
}