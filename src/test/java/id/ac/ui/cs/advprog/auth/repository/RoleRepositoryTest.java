package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @Test
    void testFindByName_ExistingRole() {
        // Mocking the behavior of the role repository
        Role role = new Role(ERole.ROLE_USER);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));

        // Testing the repository method directly
        Optional<Role> foundRole = roleRepository.findByName(ERole.ROLE_USER);

        // Verifying that the correct role is returned
        assertEquals(ERole.ROLE_USER, foundRole.get().getName());
    }

    @Test
    void testFindByName_NonExistingRole() {
        // Mocking the behavior of the role repository
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.empty());

        // Testing the repository method directly
        Optional<Role> foundRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        // Verifying that no role is found
        assertEquals(Optional.empty(), foundRole);
    }
}
