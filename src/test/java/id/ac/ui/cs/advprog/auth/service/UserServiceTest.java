package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.*;
import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.RoleRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("admin"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role(ERole.ROLE_ADMIN)));

        UserEntity userEntity = new UserEntity(registerDto.getUsername(), registerDto.getEmail(), "encodedPassword");
        userEntity.setRoles(Set.of(new Role(ERole.ROLE_ADMIN)));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity createdUser = userServiceImpl.create(registerDto);

        assertEquals(registerDto.getUsername(), createdUser.getUsername());
        assertEquals(registerDto.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testCreateUser_RoleUserBlank() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", null, "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        UserEntity userEntity = new UserEntity(registerDto.getUsername(), registerDto.getEmail(), "encodedPassword");
        userEntity.setRoles(Set.of(new Role(ERole.ROLE_USER)));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity createdUser = userServiceImpl.create(registerDto);

        assertEquals(registerDto.getUsername(), createdUser.getUsername());
        assertEquals(registerDto.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testCreateUser_RoleUser() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("user"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        UserEntity userEntity = new UserEntity(registerDto.getUsername(), registerDto.getEmail(), "encodedPassword");
        userEntity.setRoles(Set.of(new Role(ERole.ROLE_USER)));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity createdUser = userServiceImpl.create(registerDto);

        assertEquals(registerDto.getUsername(), createdUser.getUsername());
        assertEquals(registerDto.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testCreateUser_UsernameExists() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("admin"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(registerDto);
        });

        assertEquals("Username already exists: john", exception.getMessage());
    }

    @Test
    void testCreateUser_EmailExists() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("admin"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(registerDto);
        });

        assertEquals("Email already exists: john@example.com", exception.getMessage());
    }

    @Test
    void testCreateUser_RoleNotFound() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("user"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(registerDto);
        });

        assertEquals("Error: Role is not found.", exception.getMessage());
    }

    @Test
    void testCreateUser_RoleBlankNotFound() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", null, "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(registerDto);
        });

        assertEquals("Error: Role is not found.", exception.getMessage());
    }

    @Test
    void testCreateAdmin_RoleNotFound() {
        RegisterDto registerDto = new RegisterDto("john", "john@example.com", Set.of("admin"), "password123", "John Doe", "1234567890", "male", new Date());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(registerDto);
        });

        assertEquals("Error: Role is not found.", exception.getMessage());
    }

    @Test
    void testFindAll() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("john", "john@example.com", "password123"),
                new UserEntity("jane", "jane@example.com", "password456")
        );
        when(userRepository.findAll()).thenReturn(userList);

        List<UserEntity> result = userServiceImpl.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByUsername_Success() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "password123");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));

        UserDto userDto = userServiceImpl.findByUsername("john");

        assertEquals("john", userDto.getUsername());
        assertEquals("john@example.com", userDto.getEmail());
    }

    @Test
    void testFindByUsername_NotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.findByUsername("john");
        });

        assertEquals("User not found with username: john", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "encodedPassword");
        ProfileEditDto profileEditDto = new ProfileEditDto("oldPassword", "newPassword", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(profileEditDto.getOldPassword(), userEntity.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(profileEditDto.getNewPassword())).thenReturn("newEncodedPassword");

        userServiceImpl.update("john", profileEditDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals("newEncodedPassword", userEntity.getPassword());
        assertEquals("John Doe", userEntity.getFullName());
        assertEquals("johnpic.jpg", userEntity.getProfilePicture());
    }

    @Test
    void testUpdateUser_UsernameNotFound() {
        ProfileEditDto profileEditDto = new ProfileEditDto("oldPassword", "newPassword", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("juhn")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.update("juhn", profileEditDto);
        });

        assertEquals("User not found with username: juhn", exception.getMessage());
    }

    @Test
    void testUpdateUser_OldPasswordNotProvided() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "encodedPassword");
        ProfileEditDto profileEditDto = new ProfileEditDto("", "newPassword", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.update("john", profileEditDto);
        });

        assertEquals("Old password must not be blank if new password is provided", exception.getMessage());
    }

    @Test
    void testUpdateUser_OldPasswordBlank() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "encodedPassword");
        ProfileEditDto profileEditDto = new ProfileEditDto(null, "newPassword", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.update("john", profileEditDto);
        });

        assertEquals("Old password must not be blank if new password is provided", exception.getMessage());
    }

    @Test
    void testUpdateUser_OldPasswordIncorrect() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "encodedPassword");
        ProfileEditDto profileEditDto = new ProfileEditDto("oldPassword", "newPassword", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(profileEditDto.getOldPassword(), userEntity.getPassword())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.update("john", profileEditDto);
        });

        assertEquals("Old password is incorrect", exception.getMessage());
    }

    @Test
    void testUpdateUser_NoNewPassword() {
        UserEntity userEntity = new UserEntity("john", "john@example.com", "encodedPassword");
        ProfileEditDto profileEditDto = new ProfileEditDto(null, "", "John Doe", "1234567890", "johnpic.jpg", "Updated bio", "male", new Date());

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));

        userServiceImpl.update("john", profileEditDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals("encodedPassword", userEntity.getPassword()); // Password should not be changed
        assertEquals("John Doe", userEntity.getFullName());
        assertEquals("1234567890", userEntity.getPhoneNumber());
        assertEquals("johnpic.jpg", userEntity.getProfilePicture());
        assertEquals("Updated bio", userEntity.getBio());
        assertEquals("male", userEntity.getGender());
    }

    @Test
    void testLogin_Success() {
        LoginDto loginDto = new LoginDto("john", "password123");

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(UUID.randomUUID());
        when(userDetails.getUsername()).thenReturn("john");
        when(userDetails.getEmail()).thenReturn("john@example.com");

        when(jwtGenerator.generateToken(authentication)).thenReturn("jwt-token");

        AuthResponseDto responseDto = userServiceImpl.login(loginDto);

        assertEquals("jwt-token", responseDto.getToken());
        assertEquals("john", responseDto.getUsername());
        assertEquals("john@example.com", responseDto.getEmail());
    }

    @Test
    void testLogin() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        String token = "mockToken";
        UUID userId = UUID.randomUUID();
        String email = "testuser@example.com";
        List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        LoginDto loginDto = new LoginDto(username, password);

        UserDetailsImpl userDetails = new UserDetailsImpl(userId, username, email, "password", roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn(token);

        // Act
        AuthResponseDto response = userServiceImpl.login(loginDto);

        // Assert
        assertEquals(token, response.getToken());
        assertEquals(userId, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(email, response.getEmail());
        assertEquals(Collections.singletonList("ROLE_USER"), response.getRoles());
    }
}