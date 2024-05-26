package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileEditDtoTest {

    @Test
    public void testOldPasswordSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String oldPassword = "oldPassword123";

        // Act
        profileEditDto.setOldPassword(oldPassword);

        // Assert
        assertEquals(oldPassword, profileEditDto.getOldPassword());
    }

    @Test
    public void testNewPasswordSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String newPassword = "newPassword456";

        // Act
        profileEditDto.setNewPassword(newPassword);

        // Assert
        assertEquals(newPassword, profileEditDto.getNewPassword());
    }

    @Test
    public void testFullNameSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String fullName = "John Doe";

        // Act
        profileEditDto.setFullName(fullName);

        // Assert
        assertEquals(fullName, profileEditDto.getFullName());
    }

    @Test
    public void testProfilePictureSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String profilePicture = "profilePicture.png";

        // Act
        profileEditDto.setProfilePicture(profilePicture);

        // Assert
        assertEquals(profilePicture, profileEditDto.getProfilePicture());
    }

    @Test
    public void testBioSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String bio = "This is a bio.";

        // Act
        profileEditDto.setBio(bio);

        // Assert
        assertEquals(bio, profileEditDto.getBio());
    }

    @Test
    public void testGenderSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String gender = "Male";

        // Act
        profileEditDto.setGender(gender);

        // Assert
        assertEquals(gender, profileEditDto.getGender());
    }

    @Test
    public void testBirthDateSetterAndGetter() {
        // Arrange
        ProfileEditDto profileEditDto = new ProfileEditDto();
        Date birthDate = new Date();

        // Act
        profileEditDto.setBirthDate(birthDate);

        // Assert
        assertEquals(birthDate, profileEditDto.getBirthDate());
    }
}