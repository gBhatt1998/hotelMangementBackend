package com.example.hotelManagementBackend.Exception;

//package com.example.demo;

import org.springframework.http.HttpStatus;

public class CustomException extends  RuntimeException {
    private HttpStatus status;
    public CustomException(HttpStatus status,String message){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}