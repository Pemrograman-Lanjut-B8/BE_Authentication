package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;

import id.ac.ui.cs.advprog.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<AuthResponseDto>> login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    AuthResponseDto errorResponse = new AuthResponseDto();
                    errorResponse.setToken(null);
                    errorResponse.setType("Bearer");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                });
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<String>> registerUser(@Valid @RequestBody RegisterDto signUpRequest) {
        return userService.create(signUpRequest)
                .thenApply(authResponseDto -> ResponseEntity.ok("User registered successfully!"))
                .exceptionally(ex -> ResponseEntity.status(500).body("Failed to register user"));
    }
}