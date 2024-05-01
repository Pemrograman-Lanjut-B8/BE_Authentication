package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    UserEntity user;

    @BeforeEach
    public void testUserModel() {
        // Create a new user instance
        user = new UserEntity();

        // Set values for the user
        user.setUsername("John");
        user.setPassword("password123");

        // Verify the values are correctly set
    }


    @Test
    void testGetUsername() {
        assertEquals("John", user.getUsername());
    }


    @Test
    void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }
}
