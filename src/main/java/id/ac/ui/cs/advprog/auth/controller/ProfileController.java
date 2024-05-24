package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.UserDto;
import id.ac.ui.cs.advprog.auth.security.JWTGenerator;
import id.ac.ui.cs.advprog.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private JWTGenerator jwtGenerator;

    private UserService userService;

    @Autowired
    public ProfileController(JWTGenerator jwtGenerator, UserService userService) {
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // Remove the "Bearer " prefix
        String jwtToken = token.substring(7);
        String username = jwtGenerator.getUsernameFromJWT(jwtToken);

        try {
            UserDto user = userService.findByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            System.err.println("Exception occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/edit")
    public ResponseEntity<Object> editProfile(@RequestBody ProfileEditDto profileDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // Remove the "Bearer " prefix
        String jwtToken = token.substring(7);
        String username = jwtGenerator.getUsernameFromJWT(jwtToken);

        try {
            userService.update(username, profileDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + ex.getMessage());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
