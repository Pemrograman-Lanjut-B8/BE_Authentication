package id.ac.ui.cs.advprog.auth.security;

import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserEntity userEntity;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        userEntity = new UserEntity("testuser", "testuser@example.com", "password123");
        userEntity.setId(uuid);

        Role role = Mockito.mock(Role.class);
        Mockito.when(role.getName()).thenReturn(ERole.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userEntity.setRoles(roles);

        userDetails = UserDetailsImpl.build(userEntity);
    }

    @Test
    void testBuild() {
        UserDetailsImpl userDetailsFromBuild = UserDetailsImpl.build(userEntity);
        assertNotNull(userDetailsFromBuild);
        assertEquals(userEntity.getId(), userDetailsFromBuild.getId());
        assertEquals(userEntity.getUsername(), userDetailsFromBuild.getUsername());
        assertEquals(userEntity.getEmail(), userDetailsFromBuild.getEmail());
        assertEquals(userEntity.getPassword(), userDetailsFromBuild.getPassword());

        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();

        assertEquals(authorities, userDetailsFromBuild.getAuthorities());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(ERole.ROLE_USER.name())));
    }

    @Test
    void testGetId() {
        assertEquals(userEntity.getId(), userDetails.getId());
    }

    @Test
    void testGetEmail() {
        assertEquals(userEntity.getEmail(), userDetails.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals() {
        // this == o
        assertEquals(userDetails, userDetails);

        // o is null
        assertNotEquals(userDetails, null);

        // Different class
        assertNotEquals(userDetails, new Object());

        // Same class, different id
        UserDetailsImpl userDetailsDifferentId = new UserDetailsImpl(
                UUID.randomUUID(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .toList()
        );
        assertNotEquals(userDetails, userDetailsDifferentId);

        // Same class, same id
        UserDetailsImpl userDetailsSameId = new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
        assertEquals(userDetails, userDetailsSameId);

        // Same class, different username (same id)
        UserDetailsImpl userDetailsDifferentUsername = new UserDetailsImpl(
                userEntity.getId(),
                "differentuser",
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
        assertEquals(userDetails, userDetailsDifferentUsername);
    }

    @Test
    void testHashCode() {
        UserDetailsImpl userDetails1 = new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );

        UserDetailsImpl userDetails2 = new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );

        assertEquals(userDetails1.hashCode(), userDetails2.hashCode());

        UserDetailsImpl userDetails3 = new UserDetailsImpl(
                UUID.randomUUID(),
                "differentuser",
                "different@example.com",
                "differentpassword",
                new HashSet<>()
        );

        assertNotEquals(userDetails1.hashCode(), userDetails3.hashCode());
    }
}