package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepo;
    private final RoomTypeRepository roomTypeRepo;
    private final ReservationRepository reservationRepo;

    public RoomService(RoomRepository roomRepo, RoomTypeRepository roomTypeRepo, ReservationRepository reservationRepo) {
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public List<RoomResponseDTO> getAllRoomsWithDeleteFlag() {
        return roomRepo.findAll().stream()
                .map(room -> RoomMapper.toResponseDTO(room, !reservationRepo.existsByRoom(room)))
                .collect(Collectors.toList());
    }

    public Room createRoom(RoomRequestDTO dto) {
        RoomType type = roomTypeRepo.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        int base = dto.getRoomTypeId() * 100;
        int max = roomRepo.findMaxRoomNoForType(base, base + 99);
        int newRoomNo = (max == 0) ? base + 1 : max + 1;

        Room room = new Room();
        room.setRoomNo(newRoomNo);
        room.setRoomTypeId(type);
        room.setAvailability(dto.getAvailability());
        return roomRepo.save(room);
    }

    public void deleteRoom(int roomNo) {
        Room room = roomRepo.findById(roomNo)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        if (reservationRepo.existsByRoom(room)) {
            throw new RuntimeException("Cannot delete a room that has reservations.");
        }
        roomRepo.deleteById(roomNo);
    }

    public int suggestNextRoomNumber(int roomTypeId) {
        int base = roomTypeId * 100;
        int max = roomRepo.findMaxRoomNoForType(base, base + 99);
        return (max == 0) ? base + 1 : max + 1;
    }


}