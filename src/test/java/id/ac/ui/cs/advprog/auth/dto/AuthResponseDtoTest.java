package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthResponseDtoTest {

    @Test
    public void testAuthResponseDtoConstructorAndGetters() {
        // Arrange
        String accessToken = "someAccessToken";
        UUID id = UUID.randomUUID();
        String username = "john_doe";
        String email = "john@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        // Act
        AuthResponseDto authResponseDto = new AuthResponseDto(accessToken, id, username, email, roles);

        // Assert
        assertEquals(accessToken, authResponseDto.getToken());
        assertEquals("Bearer", authResponseDto.getType());
        assertEquals(id, authResponseDto.getId());
        assertEquals(username, authResponseDto.getUsername());
        assertEquals(email, authResponseDto.getEmail());
        assertEquals(roles, authResponseDto.getRoles());
    }

    @Test
    public void testAuthResponseDtoSetters() {
        // Arrange
        AuthResponseDto authResponseDto = new AuthResponseDto("oldToken", UUID.randomUUID(), "oldUsername", "old@example.com", Arrays.asList("ROLE_OLD"));

        // Act
        authResponseDto.setToken("newToken");
        authResponseDto.setUsername("newUsername");
        authResponseDto.setEmail("new@example.com");
        authResponseDto.setRoles(Arrays.asList("ROLE_NEW"));

        // Assert
        assertEquals("newToken", authResponseDto.getToken());
        assertEquals("newUsername", authResponseDto.getUsername());
        assertEquals("new@example.com", authResponseDto.getEmail());
        assertEquals(Arrays.asList("ROLE_NEW"), authResponseDto.getRoles());
    }
}
