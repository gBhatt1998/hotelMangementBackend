package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<RoomResponseDTO> getRoomsByRoomType(String type) {
        List<Room> filteredRooms = roomRepo.findByRoomType_Type(type);
        return filteredRooms.stream()
                .map(room -> RoomMapper.toResponseDTO(room, !reservationRepo.existsByRoom(room)))
                .collect(Collectors.toList());
    }

    public Room createRoom(RoomRequestDTO dto) {
        RoomType type = roomTypeRepo.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        int newRoomNo = calculateNextRoomNumber(dto.getRoomTypeId());

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
        if (!roomTypeRepo.existsById(roomTypeId)) {
            throw new RuntimeException("Room type not found");
        }
        return calculateNextRoomNumber(roomTypeId);
    }

    private int calculateNextRoomNumber(int roomTypeId) {
        int base = roomTypeId * 100;
        Integer max = roomRepo.findMaxRoomNoForType(base, base + 99);
        return (max == null || max == 0) ? base + 1 : max + 1;
    }

    public List<Integer> suggestRoomNumbers(int roomTypeId, int baseRoomNo, int count) {
        List<Integer> finalSuggestions = new ArrayList<>();
        int current = baseRoomNo;

        while (finalSuggestions.size() < count) {
            // Generate next batch of candidates
            List<Integer> candidates = new ArrayList<>();
            for (int i = 0; i < (count - finalSuggestions.size()); i++) {
                candidates.add(current + i);
            }

            // Check which candidates already exist
            List<Integer> existing = roomRepo.findExistingRoomNos(candidates);

            // Filter out existing ones
            for (int num : candidates) {
                if (!existing.contains(num)) {
                    finalSuggestions.add(num);
                }
            }

            // Move the base for next batch
            current += candidates.size();

            // Safety check: avoid infinite loop
            if (current > 9999) break;
        }

        if (finalSuggestions.size() < count) {
            throw new CustomException(
                    HttpStatus.CONFLICT,
                    "Unable to find " + count + " available room numbers starting from " + baseRoomNo
            );
        }

        return finalSuggestions;
    }


}