package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.model.ERole;
import id.ac.ui.cs.advprog.auth.model.Role;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.RoleRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private JWTGenerator jwtGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<UserEntity> create(RegisterDto user) throws RuntimeException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }

        UserEntity newUser = new UserEntity(user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = user.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = null;
                        adminRole = (Role) roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = null;
                        userRole = (Role) roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        newUser.setRoles(roles);
        newUser.setFullName(user.getFullName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setGender(user.getGender());
        newUser.setBirthDate(user.getBirthDate());
        userRepository.save(newUser);

        return CompletableFuture.completedFuture(newUser);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<List<UserEntity>> findAll() {
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<UserEntity> findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async("asyncTaskExecutor")
    public void update(String username, ProfileEditDto data) {
        UserEntity existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (data.getNewPassword() != null) {
            if (data.getOldPassword().isEmpty())
                throw new IllegalArgumentException("Old password must not be blank if new password is provided");
            else if (!passwordEncoder.matches(data.getOldPassword(), existingUser.getPassword()))
                throw new IllegalArgumentException("Old password is incorrect");
            else
                existingUser.setPassword(passwordEncoder.encode(data.getNewPassword()));
        }

        existingUser.setFullName(data.getFullName());
        existingUser.setProfilePicture(data.getProfilePicture());
        existingUser.setBio(data.getBio());
        existingUser.setGender(data.getGender());
        existingUser.setBirthDate(data.getBirthDate());

        userRepository.save(existingUser);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<AuthResponseDto> login(LoginDto user) {
        return CompletableFuture.supplyAsync(() -> {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new AuthResponseDto(token,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        });
    }
}
