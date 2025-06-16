package com.example.hotelManagementBackend.dto;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getJwt() {
        return token;
    }
}
