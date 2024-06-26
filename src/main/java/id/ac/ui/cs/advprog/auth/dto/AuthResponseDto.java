package id.ac.ui.cs.advprog.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;

    public AuthResponseDto(String accessToken, UUID id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}