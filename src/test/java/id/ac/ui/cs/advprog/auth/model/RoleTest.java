package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    public void testGetId() {
        Role role = new Role(ERole.ROLE_USER);
        role.setId(1L);
        assertEquals(1L, role.getId());
    }

    @Test
    public void testGetName() {
        Role role = new Role(ERole.ROLE_USER);
        assertEquals(ERole.ROLE_USER, role.getName());
    }

    @Test
    public void testSetName() {
        Role role = new Role();
        role.setName(ERole.ROLE_ADMIN);
        assertEquals(ERole.ROLE_ADMIN, role.getName());
    }
}
