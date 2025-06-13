package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        RoomType roomType = roomTypeRepository.findById(room.getRoomTypeId().getId())
                .orElseThrow(() -> new RuntimeException("Room type not found with id " + room.getRoomTypeId().getId()));
        room.setRoomTypeId(roomType);
        return roomRepository.save(room);
    }

    public Room updateRoom(int roomNo, Room updatedRoom) {
        Room existing = roomRepository.findById(roomNo)
                .orElseThrow(() -> new RuntimeException("Room not found with number " + roomNo));

        existing.setAvailability(updatedRoom.getAvailability());

        RoomType roomType = roomTypeRepository.findById(updatedRoom.getRoomTypeId().getId())
                .orElseThrow(() -> new RuntimeException("Room type not found with id " + updatedRoom.getRoomTypeId().getId()));

        existing.setRoomTypeId(roomType);
        return roomRepository.save(existing);
    }

    public void deleteRoom(int roomNo) {
        if (!roomRepository.existsById(roomNo)) {
            throw new RuntimeException("Room not found with number " + roomNo);
        }
        roomRepository.deleteById(roomNo);
    }
}
