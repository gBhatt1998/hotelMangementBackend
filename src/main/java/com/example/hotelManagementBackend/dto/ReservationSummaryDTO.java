package com.example.hotelManagementBackend.dto;

import java.sql.Date;
import java.util.List;

public class ReservationSummaryDTO {

    private Integer reservationId;
    private Date checkInDate;
    private Date checkOutDate;
    private float totalPrice;
    private int roomNumber;
    private String roomTypeName;
//    private List<String> serviceNames;

//    public List<String> getServiceNames() {
//        return serviceNames;
//    }

//    public void setServiceNames(List<String> serviceNames) {
//        this.serviceNames = serviceNames;
//    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }
}
