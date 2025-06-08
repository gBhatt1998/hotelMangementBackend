package com.example.hotelManagementBackend.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private String message;
    private int status;
    private Date timestamp;
    private Map<String, List<String>> validationErrors;
    public ErrorResponse(String message, int status, Date timestamp, Map<String, List<String>> validationErrors) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.validationErrors = validationErrors;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Constructors
    public ErrorResponse(String message, int status, Date timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }



    // Getters and Setters
    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, List<String>> validationErrors) {
        this.validationErrors = validationErrors;
    }

    // Existing getters/setters for other fields
}