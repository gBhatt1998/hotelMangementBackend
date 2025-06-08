package com.example.hotelManagementBackend.dto;

public class AuthResponse {

    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // getter
    public String getJwt() { return jwt; }
}
