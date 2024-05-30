package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileEditDtoTest {

    @Test
    void testOldPasswordSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String oldPassword = "oldPassword123";

        profileEditDto.setOldPassword(oldPassword);

        assertEquals(oldPassword, profileEditDto.getOldPassword());
    }

    @Test
    void testNewPasswordSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String newPassword = "newPassword456";

        profileEditDto.setNewPassword(newPassword);

        assertEquals(newPassword, profileEditDto.getNewPassword());
    }

    @Test
    void testFullNameSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String fullName = "John Doe";

        profileEditDto.setFullName(fullName);

        assertEquals(fullName, profileEditDto.getFullName());
    }

    @Test
    void testProfilePictureSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String profilePicture = "profilePicture.png";

        profileEditDto.setProfilePicture(profilePicture);

        assertEquals(profilePicture, profileEditDto.getProfilePicture());
    }

    @Test
    void testBioSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String bio = "This is a bio.";

        profileEditDto.setBio(bio);

        assertEquals(bio, profileEditDto.getBio());
    }

    @Test
    void testGenderSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        String gender = "Male";

        profileEditDto.setGender(gender);

        assertEquals(gender, profileEditDto.getGender());
    }

    @Test
    void testBirthDateSetterAndGetter() {
        ProfileEditDto profileEditDto = new ProfileEditDto();
        Date birthDate = new Date();

        profileEditDto.setBirthDate(birthDate);

        assertEquals(birthDate, profileEditDto.getBirthDate());
    }
}