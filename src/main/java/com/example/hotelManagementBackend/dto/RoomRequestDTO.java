

package com.example.hotelManagementBackend.dto;

public class RoomRequestDTO {
    private int roomTypeId;
    private Boolean availability;

    public int getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(int roomTypeId) { this.roomTypeId = roomTypeId; }

    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }
}
