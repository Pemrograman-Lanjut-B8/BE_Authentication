package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.UserEntity;
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
        UserEntity user = new UserEntity();
        user.setUsername("John Smith");
        user.setPassword("password123");

        UserEntity createdUser = userRepository.createUser(user);

        assertEquals("John Smith", createdUser.getUsername());
        assertEquals("password123", createdUser.getPassword());

        Iterator<UserEntity> iterator = userRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals(createdUser, iterator.next());
    }

    @Test
    void testFindByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("John Smith");
        user.setPassword("password123");

        UserEntity createdUser = userRepository.createUser(user);

        UserEntity foundUser = userRepository.findByUsername(createdUser.getUsername());
        assertNotNull(foundUser);
        assertEquals("John Smith", foundUser.getUsername());
        assertEquals("password123", foundUser.getPassword());
    }

    @Test
    void testUpdateUser() {
        // Create a user
        UserEntity user = new UserEntity();
        user.setUsername("John Smith");
        user.setPassword("password123");

        UserEntity createdUser = userRepository.createUser(user);
        String username = user.getUsername();

        // Update the user
        UserEntity updatedUser = new UserEntity();
        updatedUser.setUsername("Updated Name");
        updatedUser.setPassword("updatedpassword");
        UserEntity updatedUserResult = userRepository.update(username, updatedUser);

        // Verify the user has been updated
        assertNotNull(updatedUserResult);
        assertEquals("Updated Name", updatedUserResult.getUsername());
        assertEquals("updatedpassword", updatedUserResult.getPassword());

        // Verify that the updated user is reflected in the repository
        Iterator<UserEntity> iterator = userRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals(updatedUserResult, iterator.next());
    }

//    @Test
//    void testDeleteUserById() {
//        // Create a user
//        UserEntity user = new UserEntity();
//        user.setFullName("John Doe");
//        user.setEmail("john@example.com");
//        user.setPassword("password123");
//        UserEntity createdUser = userRepository.createUser(user);
//        String userId = createdUser.getId();
//
//        // Delete the user
//        userRepository.deleteUserById(userId);
//
//        // Verify the user has been deleted
//        UserEntity foundUser = userRepository.findById(userId);
//        assertNull(foundUser);
//
//        // Verify that the deleted user is no longer in the repository
//        Iterator<UserEntity> iterator = userRepository.findAll();
//        assertFalse(iterator.hasNext());
//    }

//    @Test
//    void testCreateUserWithGeneratedId() {
//        UserEntity user = new UserEntity();
//        user.setFullName("Jane Doe");
//        user.setEmail("jane@example.com");
//        user.setPassword("password456");
//
//        UserEntity createdUser = userRepository.createUser(user);
//
//        assertNotNull(createdUser.getId());
//        assertEquals("Jane Doe", createdUser.getFullName());
//        assertEquals("jane@example.com", createdUser.getEmail());
//        assertEquals("password456", createdUser.getPassword());
//
//        // Ensure generated id is unique
//        UserEntity newUser = new UserEntity();
//        newUser.setFullName("Another User");
//        newUser.setEmail("another@example.com");
//        newUser.setPassword("anotherpassword");
//        UserEntity createdNewUser = userRepository.createUser(newUser);
//
//        assertNotEquals(createdUser.getId(), createdNewUser.getId());
//    }
//
//    @Test
//    void testUpdateNonExistentUser() {
//        UserEntity user = new UserEntity();
//        user.setFullName("John Doe");
//        user.setEmail("john@example.com");
//        user.setPassword("password123");
//
//        UserEntity updatedUser = new UserEntity();
//        updatedUser.setFullName("Updated Name");
//
//        // Try to update a user that does not exist
//        assertNull(userRepository.update("nonExistentId", updatedUser));
//    }
//
//    @Test
//    void testDeleteNonExistentUser() {
//        // Try to delete a user that does not exist
//        userRepository.deleteUserById("nonExistentId");
//
//        // Ensure no exception is thrown
//        assertTrue(true);
//    }
}

