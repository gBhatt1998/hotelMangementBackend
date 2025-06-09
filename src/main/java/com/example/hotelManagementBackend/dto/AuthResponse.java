package com.example.hotelManagementBackend.dto;

public class AuthResponse {

    private String token;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role=role;
    }

    // getter
    public String getJwt() { return token; }
}
