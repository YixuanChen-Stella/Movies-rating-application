package com.stella.assignment2.controller;

import com.stella.assignment2.dto.JwtAuthResponse;
import com.stella.assignment2.dto.LoginDto;
import com.stella.assignment2.dto.SignUpDto;
import com.stella.assignment2.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = authService.login(loginDto);
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid credentials: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        try {
            String token = authService.register(signUpDto);
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
            return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}
