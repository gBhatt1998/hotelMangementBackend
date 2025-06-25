package com.example.hotelManagementBackend.dto;

import java.sql.Date;
import java.util.List;

public class ReservationDetailsResponse {
    private Long reservationId;
    private Date checkInDate;
    private Date checkOutDate;
    private float totalPrice;
    private int roomNumber;
    private String roomTypeName;
    private List<String> serviceNames;
    private GuestDetails guest; // Add guest details to every response
    private boolean canDelete;


    public static class GuestDetails {
        private int id;
        private String name;
        private String email;
        private String phone;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    // Getters and setters for all fields
    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public float getTotalPrice() { return totalPrice; }
    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
    public List<String> getServiceNames() { return serviceNames; }
    public void setServiceNames(List<String> serviceNames) { this.serviceNames = serviceNames; }
    public GuestDetails getGuest() { return guest; }
    public void setGuest(GuestDetails guest) { this.guest = guest; }
    // Getter and Setter
    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}