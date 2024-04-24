package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        when(userRepository.createUser(user)).thenReturn(user);

        User createdUser = userService.create(user);
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getFullName());
        assertEquals("john@example.com", createdUser.getEmail());
        assertEquals("password123", createdUser.getPassword());

        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        Iterator<User> iterator = userList.iterator();
        when(userRepository.findAll()).thenReturn(iterator);

        List<User> allUsers = userService.findAll();
        assertEquals(2, allUsers.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserById() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFullName("John Doe");

        when(userRepository.findById(userId)).thenReturn(user);

        User foundUser = userService.findById(userId);
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getFullName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFullName("John Doe");

        userService.update(userId, user);

        verify(userRepository, times(1)).update(userId, user);
    }

    @Test
    void testDeleteUserById() {
        String userId = "1";
        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteUserById(userId);
    }
}
