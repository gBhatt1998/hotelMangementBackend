package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RoomTypeRequestDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomTypeMapper;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private AnalyzeReservation analyzeReservation;

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public RoomType createRoomType(RoomType roomType) {
        RoomType created = roomTypeRepository.save(roomType);
        analyzeReservation.refreshRoomTypeData();
        return created;
    }

    public RoomType updateRoomType(int id, RoomTypeRequestDTO dto) {
        RoomType existing = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found with id " + id));
        RoomTypeMapper.updateEntity(existing, dto);
        RoomType updated = roomTypeRepository.save(existing);
        analyzeReservation.refreshRoomTypeData();
        return updated;
    }

    public void deleteRoomType(int id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not found with id " + id));

        List<Room> associatedRooms = roomType.getRoomList();

        for (Room room : associatedRooms) {
            if (room.getReservations() != null && !room.getReservations().isEmpty()) {
                throw new RuntimeException("Cannot delete RoomType with ID " + id +
                        " because room " + room.getRoomNo() + " has existing reservations.");
            }
        }

        roomTypeRepository.delete(roomType);
        analyzeReservation.refreshRoomTypeData();
    }
}