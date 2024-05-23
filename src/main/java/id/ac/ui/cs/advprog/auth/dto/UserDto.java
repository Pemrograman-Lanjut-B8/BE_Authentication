package id.ac.ui.cs.advprog.auth.dto;

import id.ac.ui.cs.advprog.auth.model.ERole;
import lombok.Data;
import java.util.Set;
import java.util.UUID;
import java.util.Date;

@Data
public class UserDto {
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
}
