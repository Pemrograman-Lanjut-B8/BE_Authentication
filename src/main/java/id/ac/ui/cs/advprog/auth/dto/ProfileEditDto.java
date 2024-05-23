package id.ac.ui.cs.advprog.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileEditDto {
    private String oldPassword;
    private String newPassword;
    private String fullName;
    private String profilePicture;
    private String bio;
    private String gender;
    private Date birthDate;
}
