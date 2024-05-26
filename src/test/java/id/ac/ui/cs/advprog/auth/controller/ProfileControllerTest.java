package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.UserDto;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ProfileControllerTest {

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfile_Success() {
        String token = "Bearer testtoken";
        String username = "testuser";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(userDto);

        ResponseEntity<UserDto> response = profileController.getUserProfile(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testGetUserProfile_UsernameNotFound() {
        String token = "Bearer testtoken";
        String username = "testuser";

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        when(userService.findByUsername(username)).thenThrow(new UsernameNotFoundException("Username not found"));

        ResponseEntity<UserDto> response = profileController.getUserProfile(token);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetUserProfile_InternalServerError() {
        String token = "Bearer testtoken";
        String username = "testuser";

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        when(userService.findByUsername(username)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<UserDto> response = profileController.getUserProfile(token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testEditProfile_Success() {
        String token = "Bearer testtoken";
        String username = "testuser";
        ProfileEditDto profileEditDto = new ProfileEditDto();
        profileEditDto.setOldPassword("oldpassword");
        profileEditDto.setNewPassword("newpassword");
        profileEditDto.setFullName("New FullName");
        profileEditDto.setProfilePicture("newprofilepicture.jpg");
        profileEditDto.setBio("New bio");
        profileEditDto.setGender("Other");
        profileEditDto.setBirthDate(new Date());

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);

        ResponseEntity<Object> response = profileController.editProfile(profileEditDto, token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditProfile_UsernameNotFound() {
        String token = "Bearer testtoken";
        String username = "testuser";
        ProfileEditDto profileEditDto = new ProfileEditDto();
        profileEditDto.setOldPassword("oldpassword");
        profileEditDto.setNewPassword("newpassword");
        profileEditDto.setFullName("New FullName");
        profileEditDto.setProfilePicture("newprofilepicture.jpg");
        profileEditDto.setBio("New bio");
        profileEditDto.setGender("Other");
        profileEditDto.setBirthDate(new Date());

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        doThrow(new UsernameNotFoundException("Username not found")).when(userService).update(username, profileEditDto);

        ResponseEntity<Object> response = profileController.editProfile(profileEditDto, token);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Username not found: Username not found", response.getBody());
    }

    @Test
    void testEditProfile_BadRequest() {
        String token = "Bearer testtoken";
        String username = "testuser";
        ProfileEditDto profileEditDto = new ProfileEditDto();
        profileEditDto.setOldPassword("oldpassword");
        profileEditDto.setNewPassword("newpassword");
        profileEditDto.setFullName("New FullName");
        profileEditDto.setProfilePicture("newprofilepicture.jpg");
        profileEditDto.setBio("New bio");
        profileEditDto.setGender("Other");
        profileEditDto.setBirthDate(new Date());

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        doThrow(new IllegalArgumentException("Invalid email format")).when(userService).update(username, profileEditDto);

        ResponseEntity<Object> response = profileController.editProfile(profileEditDto, token);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input: Invalid email format", response.getBody());
    }

    @Test
    void testEditProfile_InternalServerError() {
        String token = "Bearer testtoken";
        String username = "testuser";
        ProfileEditDto profileEditDto = new ProfileEditDto();
        profileEditDto.setOldPassword("oldpassword");
        profileEditDto.setNewPassword("newpassword");
        profileEditDto.setFullName("New FullName");
        profileEditDto.setProfilePicture("newprofilepicture.jpg");
        profileEditDto.setBio("New bio");
        profileEditDto.setGender("Other");
        profileEditDto.setBirthDate(new Date());

        when(jwtGenerator.getUsernameFromJWT("testtoken")).thenReturn(username);
        doThrow(new RuntimeException("Unexpected error")).when(userService).update(username, profileEditDto);

        ResponseEntity<Object> response = profileController.editProfile(profileEditDto, token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }
}