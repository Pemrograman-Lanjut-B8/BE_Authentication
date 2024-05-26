package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity userEntity;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        userEntity = new UserEntity("testuser", "testuser@example.com", "password123");
        userEntity.setId(uuid);
    }

    @Test
    void testGettersAndSetters() {
        UserEntity user = new UserEntity();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPassword("newpassword123");
        user.setFullName("New User");
        user.setPhoneNumber("123456789");
        user.setProfilePicture("profilepic.png");
        user.setBio("This is a bio");
        user.setGender("Male");
        user.setBirthDate(new Date());
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        assertEquals("newuser", user.getUsername());
        assertEquals("newuser@example.com", user.getEmail());
        assertEquals("newpassword123", user.getPassword());
        assertEquals("New User", user.getFullName());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("profilepic.png", user.getProfilePicture());
        assertEquals("This is a bio", user.getBio());
        assertEquals("Male", user.getGender());
        assertNotNull(user.getBirthDate());
        assertEquals(roles, user.getRoles());
    }
}