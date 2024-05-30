package id.ac.ui.cs.advprog.auth.dto;

import id.ac.ui.cs.advprog.auth.model.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDtoTest {

    private UserDto userDto;
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String profilePicture;
    private String bio;
    private String gender;
    private Date birthDate;
    private Set<ERole> roles;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        id = UUID.randomUUID();
        username = "testuser";
        email = "testuser@example.com";
        fullName = "Test User";
        phoneNumber = "1234567890";
        profilePicture = "profile.jpg";
        bio = "This is a bio.";
        gender = "Other";
        birthDate = new Date();
        roles = new HashSet<>();
        roles.add(ERole.ROLE_USER);
    }

    @Test
    void testSetId() {
        userDto.setId(id);
        assertEquals(id, userDto.getId());
    }

    @Test
    void testSetUsername() {
        userDto.setUsername(username);
        assertEquals(username, userDto.getUsername());
    }

    @Test
    void testSetEmail() {
        userDto.setEmail(email);
        assertEquals(email, userDto.getEmail());
    }

    @Test
    void testSetFullName() {
        userDto.setFullName(fullName);
        assertEquals(fullName, userDto.getFullName());
    }

    @Test
    void testSetPhoneNumber() {
        userDto.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, userDto.getPhoneNumber());
    }

    @Test
    void testSetProfilePicture() {
        userDto.setProfilePicture(profilePicture);
        assertEquals(profilePicture, userDto.getProfilePicture());
    }

    @Test
    void testSetBio() {
        userDto.setBio(bio);
        assertEquals(bio, userDto.getBio());
    }

    @Test
    void testSetGender() {
        userDto.setGender(gender);
        assertEquals(gender, userDto.getGender());
    }

    @Test
    void testSetBirthDate() {
        userDto.setBirthDate(birthDate);
        assertEquals(birthDate, userDto.getBirthDate());
    }

    @Test
    void testSetRoles() {
        userDto.setRoles(roles);
        assertEquals(roles, userDto.getRoles());
    }

    @Test
    void testToString() {
        userDto.setId(id);
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setFullName(fullName);
        userDto.setPhoneNumber(phoneNumber);
        userDto.setProfilePicture(profilePicture);
        userDto.setBio(bio);
        userDto.setGender(gender);
        userDto.setBirthDate(birthDate);
        userDto.setRoles(roles);

        UserDto anotherUserDto = new UserDto();
        anotherUserDto.setId(id);
        anotherUserDto.setUsername(username);
        anotherUserDto.setEmail(email);
        anotherUserDto.setFullName(fullName);
        anotherUserDto.setPhoneNumber(phoneNumber);
        anotherUserDto.setProfilePicture(profilePicture);
        anotherUserDto.setBio(bio);
        anotherUserDto.setGender(gender);
        anotherUserDto.setBirthDate(birthDate);
        anotherUserDto.setRoles(roles);

        assertEquals(userDto.toString(), anotherUserDto.toString());
    }

    @Test
    void testNotNullId() {
        userDto.setId(id);
        assertNotNull(userDto.getId());
    }

    @Test
    void testNotNullUsername() {
        userDto.setUsername(username);
        assertNotNull(userDto.getUsername());
    }

    @Test
    void testNotNullEmail() {
        userDto.setEmail(email);
        assertNotNull(userDto.getEmail());
    }

    @Test
    void testNotNullFullName() {
        userDto.setFullName(fullName);
        assertNotNull(userDto.getFullName());
    }

    @Test
    void testNotNullPhoneNumber() {
        userDto.setPhoneNumber(phoneNumber);
        assertNotNull(userDto.getPhoneNumber());
    }

    @Test
    void testNotNullProfilePicture() {
        userDto.setProfilePicture(profilePicture);
        assertNotNull(userDto.getProfilePicture());
    }

    @Test
    void testNotNullBio() {
        userDto.setBio(bio);
        assertNotNull(userDto.getBio());
    }

    @Test
    void testNotNullGender() {
        userDto.setGender(gender);
        assertNotNull(userDto.getGender());
    }

    @Test
    void testNotNullBirthDate() {
        userDto.setBirthDate(birthDate);
        assertNotNull(userDto.getBirthDate());
    }

    @Test
    void testNotNullRoles() {
        userDto.setRoles(roles);
        assertNotNull(userDto.getRoles());
    }
}