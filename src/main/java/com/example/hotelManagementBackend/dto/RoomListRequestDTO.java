package com.example.hotelManagementBackend.dto;

import java.util.List;

public class RoomListRequestDTO {
    private List<RoomRequestDTO> rooms;

    public List<RoomRequestDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomRequestDTO> rooms) {
        this.rooms = rooms;
    }
}
