package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.Auth.GuestDetails;
import com.example.hotelManagementBackend.Auth.JwtUtil;
import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.AuthRequest;
import com.example.hotelManagementBackend.dto.AuthResponse;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }

        Guest guest = guestRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "Invalid credentials."));

        final String token = jwtUtil.generateToken(new GuestDetails(guest));
        final String role = guest.getRole();

        return ResponseEntity.ok(new AuthResponse(token, role));
    }

}
