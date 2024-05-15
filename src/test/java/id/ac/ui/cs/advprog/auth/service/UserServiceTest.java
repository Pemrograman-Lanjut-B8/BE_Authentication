package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterDto registerDto;
    private ProfileEditDto profileEditDto;
    private UserEntity userEntity;
    private Role userRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setEmail("testuser@example.com");
        registerDto.setPassword("password");
        registerDto.setRole(Collections.singleton("user"));
        registerDto.setFullName("Test User");
        registerDto.setPhoneNumber("123456789");
        registerDto.setGender("Male");
        registerDto.setBirthDate(new Date());

        profileEditDto = new ProfileEditDto();
        profileEditDto.setOldPassword("password");
        profileEditDto.setNewPassword("newPassword");
        profileEditDto.setFullName("Updated User");
        profileEditDto.setProfilePicture("profile.png");
        profileEditDto.setBio("Updated Bio");
        profileEditDto.setGender("Female");
        profileEditDto.setBirthDate(new Date());

        userEntity = new UserEntity("testuser", "testuser@example.com", "encodedPassword");
        userEntity.setId(UUID.randomUUID());
        userEntity.setFullName("Test User");
        userEntity.setPhoneNumber("123456789");
        userEntity.setGender("Male");
        userEntity.setBirthDate(new Date());

        userRole = new Role(ERole.ROLE_USER);
        adminRole = new Role(ERole.ROLE_ADMIN);
    }

    @Test
    void testCreateUserSuccess() {
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        CompletableFuture<UserEntity> futureUser = userService.create(registerDto);

        assertDoesNotThrow(futureUser::join);

        verify(userRepository).existsByUsername(registerDto.getUsername());
        verify(userRepository).existsByEmail(registerDto.getEmail());
        verify(passwordEncoder).encode(registerDto.getPassword());
        verify(roleRepository).findByName(ERole.ROLE_USER);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testCreateUserUsernameAlreadyExists() {
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.create(registerDto));

        verify(userRepository).existsByUsername(registerDto.getUsername());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testCreateUserEmailAlreadyExists() {
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.create(registerDto));

        verify(userRepository).existsByUsername(registerDto.getUsername());
        verify(userRepository).existsByEmail(registerDto.getEmail());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));

        CompletableFuture<List<UserEntity>> futureUsers = userService.findAll();
        List<UserEntity> users = futureUsers.join();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(userEntity, users.get(0));

        verify(userRepository).findAll();
    }

    @Test
    void testFindByUsernameSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        CompletableFuture<UserEntity> futureUser = userService.findByUsername("testuser");
        UserEntity foundUser = futureUser.join();

        assertEquals(userEntity, foundUser);
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername("testuser"));

        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void testUpdateUserProfileSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.update("testuser", profileEditDto));

        assertEquals("Updated User", userEntity.getFullName());
        assertEquals("profile.png", userEntity.getProfilePicture());
        assertEquals("Updated Bio", userEntity.getBio());
        assertEquals("Female", userEntity.getGender());
        assertEquals(profileEditDto.getBirthDate(), userEntity.getBirthDate());
        assertEquals("newEncodedPassword", userEntity.getPassword());

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(userEntity);
    }

}