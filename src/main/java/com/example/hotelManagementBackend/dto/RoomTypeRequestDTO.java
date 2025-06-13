package com.example.hotelManagementBackend.dto;

public class RoomTypeRequestDTO {
    private String type;
    private String description;
    private float pricePerNight;
    private String imageUrl;

    // Getters and setters
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

    public float getPricePerNight() {
        return pricePerNight;
    }
    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
