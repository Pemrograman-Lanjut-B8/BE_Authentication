package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private AuthResponseDto authResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        List<String> roles = Arrays.asList("ROLE_USER");
        authResponseDto = new AuthResponseDto("mockToken", UUID.randomUUID(), "user", "user@example.com", roles);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");
        when(userService.login(any(LoginDto.class)))
                .thenReturn(CompletableFuture.completedFuture(authResponseDto));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"))
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.id").value(authResponseDto.getId().toString()))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("user");
        loginDto.setPassword("wrongPassword");
        when(userService.login(any(LoginDto.class)))
                .thenReturn(CompletableFuture.failedFuture(new Exception("Authentication failed")));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"wrongPassword\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.token").isEmpty())
                .andExpect(jsonPath("$.type").value("Bearer"));
    }


    @Test
    public void testRegisterUserConflict() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("existingUser");
        registerDto.setPassword("password");
        when(userService.create(any(RegisterDto.class)))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("User already exists")));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"existingUser\",\"password\":\"password\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("User already exists"));
    }

    @Test
    public void testRegisterUserFailure() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("newUser");
        registerDto.setPassword("password");
        when(userService.create(any(RegisterDto.class)))
                .thenReturn(CompletableFuture.failedFuture(new Exception("Database error")));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newUser\",\"password\":\"password\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Failed to register user"));
    }
}
