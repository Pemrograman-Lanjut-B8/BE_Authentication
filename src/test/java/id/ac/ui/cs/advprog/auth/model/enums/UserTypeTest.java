package id.ac.ui.cs.advprog.auth.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserTypeTest {

    @Test
    void testContains() {
        assertTrue(UserType.contains("ADMIN"));
        assertTrue(UserType.contains("CUSTOMER"));
        assertFalse(UserType.contains("SUPERUSER"));
    }

    @Test
    void testGetAll() {
        List<String> types = UserType.getAll();
        assertEquals(2, types.size());
        assertTrue(types.contains("ADMIN"));
        assertTrue(types.contains("CUSTOMER"));
    }
}

