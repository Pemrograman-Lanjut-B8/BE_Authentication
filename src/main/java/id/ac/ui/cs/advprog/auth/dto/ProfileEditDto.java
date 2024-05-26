package id.ac.ui.cs.advprog.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEditDto {
    private String oldPassword;
    private String newPassword;
    private String fullName;
    private String phoneNumber;
    private String profilePicture;
    private String bio;
    private String gender;
    private Date birthDate;
}
