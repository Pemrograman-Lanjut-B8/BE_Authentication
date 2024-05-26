package id.ac.ui.cs.advprog.auth.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDtoTest {

    @Test
    void testLoginDtoPasswordGetterAndSetter() {
        String password = "password123";

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(password);

        assertEquals(password, loginDto.getPassword());
    }

    @Test
    void testLoginDtoUsernameGetterAndSetter() {
        String username = "john_doe";

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);

        assertEquals(username, loginDto.getUsername());
    }
}
