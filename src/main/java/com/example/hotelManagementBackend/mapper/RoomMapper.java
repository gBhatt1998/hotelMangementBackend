package com.example.hotelManagementBackend.mapper;

import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;

public class RoomMapper {
    public static RoomResponseDTO toResponseDTO(Room room, boolean canDelete) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setRoomNo(room.getRoomNo());
        dto.setAvailability(room.getAvailability());
        dto.setRoomTypeId(room.getRoomTypeId().getId());
        dto.setRoomTypeName(room.getRoomTypeId().getType());
        dto.setCanDelete(canDelete);
        return dto;
    }
}
