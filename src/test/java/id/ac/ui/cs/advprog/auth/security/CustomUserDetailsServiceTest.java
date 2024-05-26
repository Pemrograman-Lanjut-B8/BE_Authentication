package id.ac.ui.cs.advprog.auth.security;

import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        userEntity = new UserEntity("testuser", "testuser@example.com", "password123");
        userEntity.setId(uuid);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknownuser");
        });

        verify(userRepository, times(1)).findByUsername("unknownuser");
    }
}