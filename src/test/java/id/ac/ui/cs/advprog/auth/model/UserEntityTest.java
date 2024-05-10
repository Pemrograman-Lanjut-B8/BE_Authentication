package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

    @Test
    public void testGetUsername() {
        UserEntity user = new UserEntity("testUser", "test@example.com", "password");
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testGetEmail() {
        UserEntity user = new UserEntity("testUser", "test@example.com", "password");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        UserEntity user = new UserEntity();
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        UserEntity user = new UserEntity("testUser", "test@example.com", "password");
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        UserEntity user = new UserEntity();
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testGetRoles() {
        UserEntity user = new UserEntity("testUser", "test@example.com", "password");
        assertEquals(0, user.getRoles().size());
    }

    @Test
    public void testSetRoles() {
        UserEntity user = new UserEntity();
        Role role = new Role(ERole.ROLE_USER);
        user.getRoles().add(role);
        assertEquals(1, user.getRoles().size());
    }
}
