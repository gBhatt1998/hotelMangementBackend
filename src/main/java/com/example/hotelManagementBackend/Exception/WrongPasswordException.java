package com.example.hotelManagementBackend.Exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends CustomException {
    public WrongPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);  // 401
    }
}
