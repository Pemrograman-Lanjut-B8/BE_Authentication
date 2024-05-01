package id.ac.ui.cs.advprog.auth.model.builder;

import id.ac.ui.cs.advprog.auth.model.UserEntity;
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
        UserEntity user = new UserBuilder()
                .addUsername("John Doe")
                .addPassword("password123")
                .build();

        assertEquals("John Doe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

}
