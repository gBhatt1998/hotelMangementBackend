package com.example.hotelManagementBackend.dto;

public class RoomTypeWithSingleRoomDTO {
    private Integer id;
    private String type;
    private String description;
    private Float pricePerNight;
    private Integer roomNumber;


    public RoomTypeWithSingleRoomDTO(Integer id, String type, String description, Float pricePerNight, Integer roomNumber) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.roomNumber = roomNumber;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}