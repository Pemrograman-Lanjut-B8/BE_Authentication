package id.ac.ui.cs.advprog.auth.model.builder;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserBuilderTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void testBuildUser() {
        User user = new UserBuilder()
                .addId("1")
                .addFullName("John Doe")
                .addEmail("john@example.com")
                .addPhoneNumber("1234567890")
                .addPassword("password123")
                .addType("CUSTOMER")
                .build();

        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertEquals("CUSTOMER", user.getType());
    }

    @Test
    void testBuildUserWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserBuilder()
                    .addId("1")
                    .addFullName("John Doe")
                    .addEmail("john@example.com")
                    .addPhoneNumber("1234567890")
                    .addPassword("password123")
                    .addType("INVALID_TYPE")
                    .build();
        });
    }
}
