package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthResponseDtoTest {

    @Test
    public void testAuthResponseDtoConstructorAndGetters() {
        // Arrange
        String accessToken = "someAccessToken";
        Long id = 123L;
        String username = "john_doe";
        String email = "john@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        // Act
        AuthResponseDto authResponseDto = new AuthResponseDto(accessToken, id, username, email, roles);

        // Assert
        assertEquals(accessToken, authResponseDto.getToken());
        assertEquals("Bearer", authResponseDto.getTokenType());
        assertEquals(id, authResponseDto.getId());
        assertEquals(username, authResponseDto.getUsername());
        assertEquals(email, authResponseDto.getEmail());
        assertEquals(roles, authResponseDto.getRoles());
    }

    @Test
    public void testAuthResponseDtoSetters() {
        // Arrange
        AuthResponseDto authResponseDto = new AuthResponseDto("oldToken", 123L, "oldUsername", "old@example.com", Arrays.asList("ROLE_OLD"));

        // Act
        authResponseDto.setAccessToken("newToken");
        authResponseDto.setTokenType("NewBearer");
        authResponseDto.setId(456L);
        authResponseDto.setUsername("newUsername");
        authResponseDto.setEmail("new@example.com");
        authResponseDto.setRoles(Arrays.asList("ROLE_NEW"));

        // Assert
        assertEquals("newToken", authResponseDto.getToken());
        assertEquals("NewBearer", authResponseDto.getTokenType());
        assertEquals(456L, authResponseDto.getId());
        assertEquals("newUsername", authResponseDto.getUsername());
        assertEquals("new@example.com", authResponseDto.getEmail());
        assertEquals(Arrays.asList("ROLE_NEW"), authResponseDto.getRoles());
    }
}
