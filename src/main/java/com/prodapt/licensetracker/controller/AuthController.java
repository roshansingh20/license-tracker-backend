package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.*;
import com.prodapt.licensetracker.entity.User;
import com.prodapt.licensetracker.repository.UserRepository;
import com.prodapt.licensetracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).get();

        String token = jwtUtil.generateToken(
                user.getEmail(), user.getRole().name());

        return new LoginResponse(token, user.getRole().name());
    }
}
