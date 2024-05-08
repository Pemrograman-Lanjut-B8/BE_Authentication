package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDtoTest {

    @Test
    public void testLoginDtoPasswordGetterAndSetter() {
        // Arrange
        String password = "password123";

        // Act
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(password);

        // Assert
        assertEquals(password, loginDto.getPassword());
    }

    @Test
    public void testLoginDtoUsernameGetterAndSetter() {
        // Arrange
        String username = "john_doe";

        // Act
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);

        // Assert
        assertEquals(username, loginDto.getUsername());
    }
}
