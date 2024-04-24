package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    User user;

    @BeforeEach
    public void testUserModel() {
        // Create a new user instance
        user = new User();

        // Set values for the user
        user.setId("1");
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPhoneNumber("1234567890");
        user.setPassword("password123");
        user.setBio("A brief bio about John Doe");
        user.setJenisKelamin("Male");
        user.setTanggalLahir("01-01-2020");
        user.setType("Regular");

        // Verify the values are correctly set
    }

    @Test
    void testGetProductId() {
        assertEquals("1", this.user.getId());
    }

    @Test
    void testGetFullName() {
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testGetEmail() {
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("1234567890", user.getPhoneNumber());
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testGetBio() {
        assertEquals("A brief bio about John Doe", user.getBio());
    }

    @Test
    void testGetJenisKelamin() {
        assertEquals("Male", user.getJenisKelamin());
    }

    @Test
    void testGetTanggalLahir() {
        // You may need to adjust this based on how you want to compare dates
        assertEquals("01-01-2020", user.getTanggalLahir());
    }

    @Test
    void testGetType() {
        assertEquals("Regular", user.getType());
    }
}
