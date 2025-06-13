package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public RoomType createRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    public RoomType updateRoomType(int id, RoomType updatedRoomType) {
        Optional<RoomType> optionalRoomType = roomTypeRepository.findById(id);
        if (optionalRoomType.isEmpty()) {
            throw new RuntimeException("Room type not found with id " + id);
        }
        RoomType existing = optionalRoomType.get();
        existing.setType(updatedRoomType.getType());
        existing.setDescription(updatedRoomType.getDescription());
        existing.setPricePerNight(updatedRoomType.getPricePerNight());
        existing.setImageUrl(updatedRoomType.getImageUrl());
        return roomTypeRepository.save(existing);
    }

    public void deleteRoomType(int id) {
        if (!roomTypeRepository.existsById(id)) {
            throw new RuntimeException("Room type not found with id " + id);
        }
        roomTypeRepository.deleteById(id);
    }
}
