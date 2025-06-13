package com.example.hotelManagementBackend.mapper;

import com.example.hotelManagementBackend.dto.RoomTypeRequestDTO;
import com.example.hotelManagementBackend.dto.RoomTypeResponseDTO;
import com.example.hotelManagementBackend.entities.RoomType;

public class RoomTypeMapper {

    public static RoomTypeResponseDTO toResponseDTO(RoomType roomType) {
        RoomTypeResponseDTO dto = new RoomTypeResponseDTO();
        dto.setId(roomType.getId());
        dto.setType(roomType.getType());
        dto.setDescription(roomType.getDescription());
        dto.setPricePerNight(roomType.getPricePerNight());
        dto.setImageUrl(roomType.getImageUrl());
        return dto;
    }

    public static RoomType toEntity(RoomTypeRequestDTO dto) {
        RoomType entity = new RoomType();
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setPricePerNight(dto.getPricePerNight());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static void updateEntity(RoomType entity, RoomTypeRequestDTO dto) {
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setPricePerNight(dto.getPricePerNight());
        entity.setImageUrl(dto.getImageUrl());
    }
}
