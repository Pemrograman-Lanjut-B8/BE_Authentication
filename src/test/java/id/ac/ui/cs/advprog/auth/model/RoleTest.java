package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RoleTest {

    @InjectMocks
    private Role role;

    @Test
    public void testGetId() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    public void testGetName() {
        role.setName("USER");
        assertEquals("USER", role.getName());
    }
}
