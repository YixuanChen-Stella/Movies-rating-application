package com.stella.assignment2.service;

import com.stella.assignment2.dto.LoginDto;
import com.stella.assignment2.dto.SignUpDto;
import com.stella.assignment2.entity.User;
import com.stella.assignment2.repository.UserRepository;
import com.stella.assignment2.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(SignUpDto signUpDto) {
        Optional<User> existingUser = userRepository.findByUserName(signUpDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User newUser = new User();
        newUser.setUserName(signUpDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        newUser.setRole("ROLE_USER");

        userRepository.save(newUser);

        return jwtTokenProvider.generateToken(newUser.getUserName(), Collections.singletonList(newUser.getRole()));
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByUserName(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtTokenProvider.generateToken(user.getUserName(), Collections.singletonList(user.getRole()));
    }
}
