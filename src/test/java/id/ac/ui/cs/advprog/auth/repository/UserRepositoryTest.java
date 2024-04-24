package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userRepository.setUserBuilder(new UserBuilder());
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        User createdUser = userRepository.createUser(user);

        assertNotNull(createdUser.getId());
        assertEquals("John Doe", createdUser.getFullName());
        assertEquals("john@example.com", createdUser.getEmail());
        assertEquals("password123", createdUser.getPassword());

        Iterator<User> iterator = userRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals(createdUser, iterator.next());
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        User createdUser = userRepository.createUser(user);
        String userId = createdUser.getId();

        User foundUser = userRepository.findById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("John Doe", foundUser.getFullName());
        assertEquals("john@example.com", foundUser.getEmail());
        assertEquals("password123", foundUser.getPassword());
    }

    @Test
    void testUpdateUser() {
        // Create a user
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        User createdUser = userRepository.createUser(user);
        String userId = createdUser.getId();

        // Update the user
        User updatedUser = new User();
        updatedUser.setFullName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("updatedpassword");
        User updatedUserResult = userRepository.update(userId, updatedUser);

        // Verify the user has been updated
        assertNotNull(updatedUserResult);
        assertEquals(userId, updatedUserResult.getId());
        assertEquals("Updated Name", updatedUserResult.getFullName());
        assertEquals("updated@example.com", updatedUserResult.getEmail());
        assertEquals("updatedpassword", updatedUserResult.getPassword());

        // Verify that the updated user is reflected in the repository
        Iterator<User> iterator = userRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals(updatedUserResult, iterator.next());
    }

    @Test
    void testDeleteUserById() {
        // Create a user
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        User createdUser = userRepository.createUser(user);
        String userId = createdUser.getId();

        // Delete the user
        userRepository.deleteUserById(userId);

        // Verify the user has been deleted
        User foundUser = userRepository.findById(userId);
        assertNull(foundUser);

        // Verify that the deleted user is no longer in the repository
        Iterator<User> iterator = userRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testCreateUserWithGeneratedId() {
        User user = new User();
        user.setFullName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setPassword("password456");

        User createdUser = userRepository.createUser(user);

        assertNotNull(createdUser.getId());
        assertEquals("Jane Doe", createdUser.getFullName());
        assertEquals("jane@example.com", createdUser.getEmail());
        assertEquals("password456", createdUser.getPassword());

        // Ensure generated id is unique
        User newUser = new User();
        newUser.setFullName("Another User");
        newUser.setEmail("another@example.com");
        newUser.setPassword("anotherpassword");
        User createdNewUser = userRepository.createUser(newUser);

        assertNotEquals(createdUser.getId(), createdNewUser.getId());
    }

    @Test
    void testUpdateNonExistentUser() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        User updatedUser = new User();
        updatedUser.setFullName("Updated Name");

        // Try to update a user that does not exist
        assertNull(userRepository.update("nonExistentId", updatedUser));
    }

    @Test
    void testDeleteNonExistentUser() {
        // Try to delete a user that does not exist
        userRepository.deleteUserById("nonExistentId");

        // Ensure no exception is thrown
        assertTrue(true);
    }
}

