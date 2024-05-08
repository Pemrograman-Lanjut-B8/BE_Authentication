package id.ac.ui.cs.advprog.auth.dto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RegisterDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidRegisterDto() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("john_doe");
        registerDto.setEmail("john.doe@example.com");
        registerDto.setPassword("password123");

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        registerDto.setRole(roles);

        Set<String> violations = validateAndGetViolations(registerDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUsername() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("a"); // Too short, violates @Size(min = 3)

        Set<String> violations = validateAndGetViolations(registerDto);
        assertEquals(3, violations.size());
        assertTrue(violations.contains("username"));
    }

    @Test
    void testInvalidEmail() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("notanemail"); // Invalid email format

        Set<String> violations = validateAndGetViolations(registerDto);
        assertEquals(3, violations.size());
        assertTrue(violations.contains("email"));
    }

    @Test
    void testInvalidPassword() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setPassword("a"); // Too short, violates @Size(min = 3)

        Set<String> violations = validateAndGetViolations(registerDto);
        assertEquals(3, violations.size());
        assertTrue(violations.contains("password"));
    }

    private Set<String> validateAndGetViolations(RegisterDto registerDto) {
        return validator.validate(registerDto).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }
}
