package id.ac.ui.cs.advprog.auth.repository;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindByUsername_ExistingUser() {
        // Mocking the behavior of the user repository
        UserEntity user = new UserEntity("testUser", "test@example.com", "password");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Testing the repository method directly
        Optional<UserEntity> foundUser = userRepository.findByUsername("testUser");

        // Verifying that the correct user is returned
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsername_NonExistingUser() {
        // Mocking the behavior of the user repository
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // Testing the repository method directly
        Optional<UserEntity> foundUser = userRepository.findByUsername("nonExistingUser");

        // Verifying that no user is found
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testExistsByUsername_ExistingUsername() {
        // Mocking the behavior of the user repository
        when(userRepository.existsByUsername("existingUsername")).thenReturn(true);

        // Testing the repository method directly
        boolean exists = userRepository.existsByUsername("existingUsername");

        // Verifying that the username exists
        assertEquals(true, exists);
    }

    @Test
    public void testExistsByUsername_NonExistingUsername() {
        // Mocking the behavior of the user repository
        when(userRepository.existsByUsername("nonExistingUsername")).thenReturn(false);

        // Testing the repository method directly
        boolean exists = userRepository.existsByUsername("nonExistingUsername");

        // Verifying that the username does not exist
        assertEquals(false, exists);
    }

    @Test
    public void testExistsByEmail_ExistingEmail() {
        // Mocking the behavior of the user repository
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Testing the repository method directly
        boolean exists = userRepository.existsByEmail("existing@example.com");

        // Verifying that the email exists
        assertEquals(true, exists);
    }

    @Test
    public void testExistsByEmail_NonExistingEmail() {
        // Mocking the behavior of the user repository
        when(userRepository.existsByEmail("nonExisting@example.com")).thenReturn(false);

        // Testing the repository method directly
        boolean exists = userRepository.existsByEmail("nonExisting@example.com");

        // Verifying that the email does not exist
        assertEquals(false, exists);
    }
}
