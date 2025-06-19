package com.example.hotelManagementBackend.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

public class ReservationRequest {
    @NotNull(message = "Check-in date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @NotNull(message = "Check-out date is required")
//    @Future(message = "Check-out date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;

    @Positive(message = "Total price must be positive")
    private float totalPrice;
    @Positive(message = "Room ID must be a positive number")
    private int roomId;



    private List<@Positive(message = "Service ID must be a positive number")Integer> serviceIds; // IDs of selected services



    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }



    public List<Integer> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Integer> serviceIds) {
        this.serviceIds = serviceIds;
    }

    // Getters and Setters

}