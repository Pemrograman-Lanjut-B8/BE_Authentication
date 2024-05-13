package id.ac.ui.cs.advprog.auth.dto;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 3, max = 40)
    private String password;

    private String fullName;

    private String phoneNumber;

    private String gender;

    private Date birthDate;
}